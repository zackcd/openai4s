package openai.module.moderation

import cats.syntax.all._
import openai.Utilities.getHeaders
import openai.{BaseUrl, OpenAiClient, OpenAiConfig}
import openai.http.{OpenAiHttpClient, RequestMethod}
import openai.http.OpenAiHttpClient.executeRequest
import openai.module.moderation.domain.{CreateModerationRequest, CreateModerationResponse}

import scala.concurrent.{ExecutionContext, Future}

sealed trait ModerationClient extends OpenAiClient {

  val ResourcePath = "/v1/moderations"

  /**
   * * Classifies if text violates OpenAI's Content Policy
   */
  def create(request: CreateModerationRequest): Future[CreateModerationResponse]

}

object ModerationClient {

  def apply(config: OpenAiConfig, client: OpenAiHttpClient)(implicit
      ec: ExecutionContext
  ): ModerationClient = new ModerationClient {
    /**
     * * Classifies if text violates OpenAI's Content Policy
     */
    def create(request: CreateModerationRequest): Future[CreateModerationResponse] =
      executeRequest[CreateModerationResponse](client)(
        BaseUrl + ResourcePath,
        RequestMethod.Post,
        getHeaders(config),
        request.some
      )
  }

}
