package openai.module.model

import openai.{BaseUrl, OpenAiConfig}
import openai.Utilities.getHeaders
import openai.http.OpenAiHttpClient.executeRequest
import openai.http.{OpenAiHttpClient, RequestMethod}
import openai.module.model.domain.{GetModelsResponse, Model}

import scala.concurrent.{ExecutionContext, Future}

sealed trait ModelClient {

  val ResourcePath = "/v1/models"

  def getAll: Future[GetModelsResponse]

  def getById(modelId: String): Future[Model]
}

object ModelClient {
  def apply(
      config: OpenAiConfig,
      client: OpenAiHttpClient
  )(implicit ec: ExecutionContext): ModelClient =
    new ModelClient {

      def getAll: Future[GetModelsResponse] =
        executeRequest[GetModelsResponse](client)(
          BaseUrl + ResourcePath,
          RequestMethod.Get,
          getHeaders(config)
        )

      def getById(modelId: String): Future[Model] =
        executeRequest[Model](client)(
          BaseUrl + ResourcePath + s"/$modelId",
          RequestMethod.Get,
          getHeaders(config)
        )

    }
}
