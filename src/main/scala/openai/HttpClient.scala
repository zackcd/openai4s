package openai

import cats.effect.{IO, Resource}
import org.http4s.client.Client
import org.http4s.ember.client.EmberClientBuilder

trait HttpClient {

  private val httpClient: Resource[IO, Client[IO]] =
    EmberClientBuilder
      .default[IO]
      .build

}