package openai.completion

import cats.syntax.all._
import openai.Utilities.getHeaders
import openai.{BaseUrl, OpenAiConfig}
import openai.completion.domain._
import openai.http.{OpenAiHttpClient, RequestMethod}

import scala.concurrent.{ExecutionContext, Future}

sealed trait CompletionClient {

  val ResourcePath = "/v1/completions"

  def ask(
      request: CreateCompletionRequest
  ): Future[CreateCompletionResponse]

}

object CompletionClient extends OpenAiHttpClient {

  def apply(
      config: OpenAiConfig
  )(implicit ec: ExecutionContext): CompletionClient =
    new CompletionClient {

      def ask(
          request: CreateCompletionRequest
      ): Future[CreateCompletionResponse] =
        executeRequest[CreateCompletionResponse](
          BaseUrl + ResourcePath,
          RequestMethod.Post,
          getHeaders(config),
          request.some
        )

    }

}
