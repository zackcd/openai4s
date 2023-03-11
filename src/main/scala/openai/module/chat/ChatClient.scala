package openai.module.chat

import cats.syntax.all._
import openai.Utilities.getHeaders
import openai.{BaseUrl, OpenAiConfig}
import openai.http.{OpenAiHttpClient, RequestMethod}
import openai.http.OpenAiHttpClient.executeRequest
import openai.module.chat.domain.{
  CreateChatCompletionRequest,
  CreateChatCompletionResponse
}

import scala.concurrent.{ExecutionContext, Future}

/** Given a chat conversation, the model will return a chat completion response.
  *
  * @see
  *   https://platform.openai.com/docs/api-reference/chat
  */
sealed trait ChatClient {

  val ResourcePath = "/v1/chat"

  /** Creates a completion for the chat message
    * @see
    *   https://platform.openai.com/docs/api-reference/chat/create
    * @param request
    *   The data to use for this request
    * @return
    *   The chat completion and its metadata
    */
  def createCompletion(
      request: CreateChatCompletionRequest
  ): Future[CreateChatCompletionResponse]

}

object ChatClient {

  def apply(
      config: OpenAiConfig,
      client: OpenAiHttpClient
  )(implicit ec: ExecutionContext): ChatClient =
    new ChatClient {

      def createCompletion(
          request: CreateChatCompletionRequest
      ): Future[CreateChatCompletionResponse] =
        executeRequest[CreateChatCompletionResponse](client)(
          BaseUrl + ResourcePath + "/completions",
          RequestMethod.Post,
          getHeaders(config),
          request.some
        )

    }

}
