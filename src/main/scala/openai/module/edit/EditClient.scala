package openai.module.edit

import cats.syntax.all._
import openai.Utilities.getHeaders
import openai.{BaseUrl, OpenAiConfig}
import openai.module.edit.domain.{CreateEditRequest, Edit}
import openai.http.{OpenAiHttpClient, RequestMethod}
import openai.http.OpenAiHttpClient.executeRequest

import scala.concurrent.{ExecutionContext, Future}

/** Given a prompt and an instruction, the model will return an edited version
  * of the prompt.
  * @see
  *   https://platform.openai.com/docs/api-reference/edits
  */
sealed trait EditClient {

  /** Creates a new edit for the provided input, instruction, and parameters.
    * @see
    *   https://platform.openai.com/docs/api-reference/edits/create
    * @param request
    *   The data to use for this request
    * @return
    *   The edit and its metadata
    */
  def create(request: CreateEditRequest): Future[Edit]

}

object EditClient {

  private val ResourcePath = "/v1/edits"

  def apply(config: OpenAiConfig, client: OpenAiHttpClient)(implicit
      ec: ExecutionContext
  ): EditClient =
    new EditClient {

      def create(request: CreateEditRequest): Future[Edit] =
        executeRequest[Edit](client)(
          BaseUrl + ResourcePath,
          RequestMethod.Post,
          getHeaders(config),
          request.some
        )

    }

}
