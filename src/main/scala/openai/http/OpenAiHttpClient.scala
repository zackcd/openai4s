package openai.http

import io.circe.{Decoder, Encoder}
import io.circe.syntax.EncoderOps
import io.circe.parser.decode
import openai.module.domain.error.OpenAiApiError
import cats.syntax.all._
import openai.domain.OpenAiRequest
import sttp.capabilities
import sttp.client3._

import java.net.http.HttpClient
import scala.concurrent.{ExecutionContext, Future}

sealed trait OpenAiHttpClient

object OpenAiHttpClient {

  final case class DefaultHttpClient(
      backend: SttpBackend[Future, capabilities.WebSockets] =
        HttpClientFutureBackend.usingClient(HttpClient.newHttpClient())
  ) extends OpenAiHttpClient

  def executeRequest[R](httpClient: OpenAiHttpClient)(
      url: String,
      method: RequestMethod,
      headers: Map[String, String],
      requestBody: Option[OpenAiRequest] = None
  )(implicit
      ec: ExecutionContext,
      decoder: Decoder[R]
  ): Future[R] = {
    val request = buildRequest(url, method, headers, requestBody)

    httpClient match {
      case client: DefaultHttpClient =>
        executeDefaultClientRequest(client, request)
    }
  }

  def executeMultipartRequest[R](httpClient: OpenAiHttpClient)(
      url: String,
      method: RequestMethod,
      headers: Map[String, String],
      requestParts: Map[String, RequestPart]
  )(implicit
      ec: ExecutionContext,
      decoder: Decoder[R]
  ): Future[R] = {
    val request = buildMultipartRequest(url, method, headers, requestParts)

    httpClient match {
      case client: DefaultHttpClient =>
        executeDefaultClientRequest(client, request)
    }
  }

  private def executeDefaultClientRequest[R](
      client: DefaultHttpClient,
      request: Request[String, Any]
  )(implicit
      ec: ExecutionContext,
      decoder: Decoder[R]
  ): Future[R] = {
    client.backend.send(request).flatMap { res =>
      (if (res.code.isSuccess)
         decode[R](res.body).leftMap(e => OpenAiApiError(e.getMessage))
       else
         OpenAiApiError(s"Error executing request: ${res.body}").asLeft)
        .liftTo[Future]
    }
  }

  private def buildRequest[T](
      url: String,
      method: RequestMethod,
      headers: Map[String, String],
      body: Option[T]
  )(implicit encoder: Encoder[T]): Request[String, Any] = {

    val request = (body match {
      case Some(b) =>
        basicRequest
          .body(b.asJson.deepDropNullValues.noSpaces)
      case None => basicRequest
    }).headers(headers)
      .response(
        asString.getRight
      )

    method match {
      case RequestMethod.Get    => request.get(uri"$url")
      case RequestMethod.Post   => request.post(uri"$url")
      case RequestMethod.Put    => request.put(uri"$url")
      case RequestMethod.Delete => request.delete(uri"$url")
    }
  }

  private def buildMultipartRequest(
      url: String,
      method: RequestMethod,
      headers: Map[String, String],
      requestParts: Map[String, RequestPart]
  ): Request[String, Any] = {
    val parts = requestParts.map { case (key, value) =>
      value match {
        case RequestPart.FilePart(p) => multipartFile(key, p)
        case RequestPart.StringPart(p) =>
          multipart(key, p.asJson.deepDropNullValues.noSpaces)
      }
    }.toList

    val request = basicRequest
      .multipartBody(parts)
      .headers(headers)
      .response(
        asString.getRight
      )

    method match {
      case RequestMethod.Get    => request.get(uri"$url")
      case RequestMethod.Post   => request.post(uri"$url")
      case RequestMethod.Put    => request.put(uri"$url")
      case RequestMethod.Delete => request.delete(uri"$url")
    }
  }

}
