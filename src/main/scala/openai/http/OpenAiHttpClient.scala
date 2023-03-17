package openai.http

import io.circe.{Decoder, Encoder}
import io.circe.syntax.EncoderOps
import io.circe.parser.decode
import cats.syntax.all._
import openai.domain.OpenAiRequest
import openai.domain.error.OpenAiApiError
import sttp.capabilities
import sttp.client3._
import sttp.model.MediaType

import java.net.http.HttpClient
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

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

  def executeFactoryRequest[R](httpClient: OpenAiHttpClient)(
      url: String,
      method: RequestMethod,
      headers: Map[String, String],
      requestBody: Option[OpenAiRequest] = None
  )(implicit
      ec: ExecutionContext,
      fac: String => R
  ): Future[R] = {
    val request = buildRequest(url, method, headers, requestBody)

    httpClient match {
      case client: DefaultHttpClient =>
        executeFactoryDefaultClientRequest(client, request)
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

  private def executeFactoryDefaultClientRequest[R](
      client: DefaultHttpClient,
      request: Request[String, Any]
  )(implicit
      ec: ExecutionContext,
      fac: String => R
  ): Future[R] = {
    client.backend.send(request).flatMap { res =>
      (if (res.code.isSuccess)
         Try { fac(res.body) }.toEither
           .leftMap(e => OpenAiApiError(e.getMessage))
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
          .contentType(MediaType.ApplicationJson)
      case None => basicRequest
    }).headers(headers ++ Map("Content-Type" -> "application/json"))
      .response(
        asString.getRight
      )

    addUrl(method, url, request)
  }

  private def addUrl(
      method: RequestMethod,
      url: String,
      request: RequestT[Empty, String, Any]
  ): Request[String, Any] =
    method match {
      case RequestMethod.Get    => request.get(uri"$url")
      case RequestMethod.Post   => request.post(uri"$url")
      case RequestMethod.Put    => request.put(uri"$url")
      case RequestMethod.Delete => request.delete(uri"$url")
    }

  private def buildMultipartRequest(
      url: String,
      method: RequestMethod,
      headers: Map[String, String],
      requestParts: Map[String, RequestPart]
  ): Request[String, Any] = {
    val parts = requestParts.map { case (key, value) =>
      value match {
        case RequestPart.FilePart(p)   => multipartFile(key, p)
        case RequestPart.StringPart(p) => multipart(key, p)
        case RequestPart.IntPart(p)    => multipart(key, p.toString)
        case RequestPart.DoublePart(p) => multipart(key, p.toString)
      }
    }.toList

    val request = basicRequest
      .multipartBody(parts)
      .contentType(MediaType.MultipartFormData)
      .headers(headers ++ Map("Content-Type" -> "multipart/form-data"))
      .response(
        asString.getRight
      )

    addUrl(method, url, request)
  }

}
