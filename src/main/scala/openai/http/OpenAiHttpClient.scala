package openai.http

import io.circe.{Decoder, Encoder}
import io.circe.syntax.EncoderOps
import io.circe.parser.decode
import openai.error.OpenAiApiError
import cats.syntax.all._
import openai.OpenAiRequest

import java.net.http.HttpRequest.BodyPublishers
import java.net.http.{HttpClient, HttpRequest, HttpResponse}
import scala.jdk.FutureConverters._
import scala.concurrent.{ExecutionContext, Future}

trait OpenAiHttpClient {

  private val httpClient: HttpClient = HttpClient.newBuilder().build()

  def executeRequest[R](
      url: String,
      method: RequestMethod,
      headers: Map[String, String],
      requestBody: Option[OpenAiRequest] = None
  )(implicit
      ec: ExecutionContext,
      decoder: Decoder[R]
  ): Future[R] = {
    val uri = java.net.URI.create(url)
    val requestBuilder = HttpRequest
      .newBuilder()
      .uri(uri)
      .headers(headers.toList.flatten { case (a, b) =>
        List(a, b)
      }: _*)

    val request = buildRequest(method, requestBuilder, requestBody)

    httpClient
      .sendAsync(request, HttpResponse.BodyHandlers.ofString())
      .asScala
      .flatMap { res =>
        Future.fromTry({
          if (res.statusCode() == 200)
            decode[R](res.body())
          else
            Either.left(
              OpenAiApiError(s"Error executing request: ${res.body()}")
            )
        }.toTry)
      }
  }

  private def buildRequest[T](
      method: RequestMethod,
      request: HttpRequest.Builder,
      body: Option[T]
  )(implicit encoder: Encoder[T]): HttpRequest = {
    method match {
      case RequestMethod.Get => request.GET()
      case RequestMethod.Post =>
        request.POST(BodyPublishers.ofString(body.get.asJson.deepDropNullValues.noSpaces))
      case RequestMethod.Put =>
        request.PUT(BodyPublishers.ofString(body.get.asJson.deepDropNullValues.noSpaces))
      case RequestMethod.Delete => request.DELETE()
    }
  }.build()

}
