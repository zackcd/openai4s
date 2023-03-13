package openai.module.completion

import cats.syntax.all._
import openai.Utilities.getHeaders
import openai.{BaseUrl, OpenAiClient, OpenAiConfig}
import openai.module.completion.domain._
import openai.http.OpenAiHttpClient.executeRequest
import openai.http.{OpenAiHttpClient, RequestMethod}

import scala.concurrent.{ExecutionContext, Future}

/** Given a prompt, the model will return one or more predicted completions, and
  * can also return the probabilities of alternative tokens at each position.
  * @see
  *   https://platform.openai.com/docs/api-reference/completions
  */
sealed trait CompletionClient extends OpenAiClient {

  val ResourcePath = "/v1/completions"

  /** Creates a completion for the provided prompt and parameters
    * @see
    *   https://platform.openai.com/docs/api-reference/completions/create
    * @param request
    *   The data to use for this request
    * @return
    *   The completion and its metadata
    */
  def create(
      request: CreateCompletionRequest
  ): Future[Completion]

}

object CompletionClient {

  def apply(
      config: OpenAiConfig,
      client: OpenAiHttpClient
  )(implicit ec: ExecutionContext): CompletionClient =
    new CompletionClient {

      def create(
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
