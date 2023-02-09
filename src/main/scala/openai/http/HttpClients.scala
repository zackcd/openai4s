package openai.http

import cats.syntax.all._
import cats.effect.{Async, IO, Resource}
import openai.OpenAiConfig

import java.net.http.{HttpClient, HttpRequest, HttpResponse}

trait HttpClients[F[_]] {
  def send(req: HttpRequest): F[HttpResponse[String]]
}

object HttpClients {
  def newClients[F[_]](implicit F: Async[F]): F[HttpClients[F]] =
    F.delay {
      HttpClient.newBuilder
        .followRedirects(HttpClient.Redirect.ALWAYS)
        .build()
    }.map { client =>
      new HttpClients[F] {
        override def send(req: HttpRequest): F[HttpResponse[String]] =
          F.async { cb =>
            client.sendAsync(req, HttpResponse.BodyHandlers.ofString).handle {
              (res: HttpResponse[String], err: Throwable) =>
                if (err == null) cb(Right(res))
                else cb(Left(err))
            }
          }
      }
    }
}
