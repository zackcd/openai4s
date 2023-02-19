package openai.model

import openai.{BaseUrl, OpenAiConfig}
import openai.model.domain.{GetModelsResponse, Model}
import openai.Utilities.getHeaders
import openai.http.{OpenAiHttpClient, RequestMethod}

import scala.concurrent.{ExecutionContext, Future}

sealed trait ModelClient {

  val ResourcePath = "/v1/models"

  def getAll: Future[GetModelsResponse]

  def getById(modelId: String): Future[Model]
}

object ModelClient extends OpenAiHttpClient {
  def apply(
      config: OpenAiConfig
  )(implicit ec: ExecutionContext): ModelClient =
    new ModelClient {

      def getAll: Future[GetModelsResponse] =
        executeRequest[GetModelsResponse](
          BaseUrl + ResourcePath,
          RequestMethod.Get,
          getHeaders(config)
        )

      def getById(modelId: String): Future[Model] =
        executeRequest[Model](
          BaseUrl + ResourcePath + s"/$modelId",
          RequestMethod.Get,
          getHeaders(config)
        )

    }
}
