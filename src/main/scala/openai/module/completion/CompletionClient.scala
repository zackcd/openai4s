package openai.module.completion

import cats.syntax.all._
import openai.Utilities.getHeaders
import openai.{BaseUrl, OpenAiClient, OpenAiConfig}
import openai.module.completion.domain._
import openai.http.OpenAiHttpClient.executeRequest
import openai.http.{OpenAiHttpClient, RequestMethod}

import scala.concurrent.{ExecutionContext, Future}

sealed trait CompletionClient extends OpenAiClient {

  val ResourcePath = "/v1/completions"

  def ask(
      request: CreateCompletionRequest
  ): Future[Completion]

}

object CompletionClient {

  def apply(
      config: OpenAiConfig,
      client: OpenAiHttpClient
  )(implicit ec: ExecutionContext): CompletionClient =
    new CompletionClient {

      def ask(
          request: CreateCompletionRequest
      ): Future[Completion] =
        executeRequest[Completion](client)(
          BaseUrl + ResourcePath,
          RequestMethod.Post,
          getHeaders(config),
          request.some
        )

    }

}
