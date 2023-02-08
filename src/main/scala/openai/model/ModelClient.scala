package openai.model

import cats.syntax.all._
import cats.effect.{IO, Resource}
import openai.Utilities.getHeaders
import openai.{BaseUrl, HttpClient, OpenAiConfig}
import openai.model.domain.{GetModelsResponse, Model}
import org.http4s.circe.CirceEntityCodec.circeEntityDecoder
import org.http4s.{Method, Request, Uri}
import org.http4s.client.Client
import org.http4s.ember.client.EmberClientBuilder

sealed trait ModelClient extends HttpClient {

  def getAll: IO[GetModelsResponse]

  def getById(modelId: String): IO[Model]
}

object ModelClient {
  def apply(config: OpenAiConfig): ModelClient = new ModelClient {

    private val httpClient: Resource[IO, Client[IO]] =
      EmberClientBuilder
        .default[IO]
        .build

    def getAll: IO[GetModelsResponse] =
      Uri
        .fromString(BaseUrl + "/v1/models")
        .liftTo[IO]
        .flatMap { uri =>
          val req =
            Request[IO](Method.GET, uri)
              .withHeaders(
                getHeaders(config)
              )
          httpClient.use(_.expect[GetModelsResponse](req))
        }

    def getById(modelId: String): IO[Model] =
      Uri
        .fromString(BaseUrl + "/v1/models/" + modelId)
        .liftTo[IO]
        .flatMap { uri =>
          val req =
            Request[IO](Method.GET, uri)
              .withHeaders(
                getHeaders(config)
              )
          httpClient.use(_.expect[Model](req))
        }

  }
}
