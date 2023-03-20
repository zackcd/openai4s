package openai.module.moderation

import cats.syntax.all._
import openai.Utilities.getHeaders
import openai.{BaseUrl, OpenAiConfig}
import openai.http.{OpenAiHttpClient, RequestMethod}
import openai.http.OpenAiHttpClient.executeRequest
import openai.module.moderation.domain.{
  CreateModerationRequest,
  CreateModerationResponse
}

import scala.concurrent.{ExecutionContext, Future}

/** Given a input text, outputs if the model classifies it as violating OpenAI's
  * content policy.
  * @see
  *   https://platform.openai.com/docs/api-reference/moderations
  */
sealed trait ModerationClient {

  /** Classifies if text violates OpenAI's Content Policy
    * @see
    *   https://platform.openai.com/docs/api-reference/moderations/create
    * @param request
    *   The data to use for this request.
    * @return
    *   The moderation and its metadata.
    */
  def create(request: CreateModerationRequest): Future[CreateModerationResponse]

}

object ModerationClient {

  private val ResourcePath = "/v1/moderations"

  def apply(config: OpenAiConfig, client: OpenAiHttpClient)(implicit
      ec: ExecutionContext
  ): ModerationClient = new ModerationClient {

    def create(
        request: CreateModerationRequest
    ): Future[CreateModerationResponse] =
      executeRequest[CreateModerationResponse](client)(
        BaseUrl + ResourcePath,
        RequestMethod.Post,
        getHeaders(config),
        request.some
      )
  }

}
