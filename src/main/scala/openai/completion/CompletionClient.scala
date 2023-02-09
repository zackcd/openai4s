package openai.completion

import cats.effect.{IO, Resource}
import cats.effect.unsafe.implicits.global
import cats.syntax.all._
import io.circe.syntax.EncoderOps
import openai.Utilities.getHeaders
import openai.{BaseUrl, OpenAiConfig}
import openai.completion.domain._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.{Header, Method, Request, Uri}
import org.http4s.client.Client
import org.http4s.ember.client.EmberClientBuilder
import org.typelevel.ci.CIStringSyntax

sealed trait CompletionClient {

  def ask(
      request: CreateCompletionRequest
  ): IO[CreateCompletionResponse]

}

object CompletionClient {

  def apply(config: OpenAiConfig): CompletionClient =
    new CompletionClient {

      private val httpClient: Resource[IO, Client[IO]] =
        EmberClientBuilder
          .default[IO]
          .build

      def ask(
          request: CreateCompletionRequest
      ): IO[CreateCompletionResponse] =
        Uri
          .fromString(BaseUrl + "/v1/completions")
          .liftTo[IO]
          .flatMap { uri =>
            val req =
              Request[IO](Method.POST, uri)
                .withEntity(request.asJson.deepDropNullValues)
                .withHeaders(
                  getHeaders(config)
                )
            httpClient.use(_.expect[CreateCompletionResponse](req))
          }

    }

}
