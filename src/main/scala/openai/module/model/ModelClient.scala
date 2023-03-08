package openai.module.model

import openai.{BaseUrl, OpenAiConfig}
import openai.Utilities.getHeaders
import openai.http.OpenAiHttpClient.executeRequest
import openai.http.{OpenAiHttpClient, RequestMethod}
import openai.module.model.domain.{GetModelsResponse, Model}

import scala.concurrent.{ExecutionContext, Future}

/** List and describe the various models available in the API. You can refer to
  * the Models documentation to understand what models are available and the
  * differences between them.
  * @see
  *   https://platform.openai.com/docs/api-reference/models
  */
sealed trait ModelClient {

  val ResourcePath = "/v1/models"

  /** Lists the currently available models, and provides basic information about
    * each one such as the owner and availability.
    * @see
    *   https://platform.openai.com/docs/api-reference/models/list
    * @return
    *   The list of models.
    */
  def listModels: Future[GetModelsResponse]

  /** Retrieves a model instance, providing basic information about the model
    * such as the owner and permissioning.
    * @see
    *   https://platform.openai.com/docs/api-reference/models/retrieve
    * @param modelId
    *   The ID of the model to use for this request
    * @return
    *   The model
    */
  def retrieveModel(modelId: String): Future[Model]
}

object ModelClient {
  def apply(
      config: OpenAiConfig,
      client: OpenAiHttpClient
  )(implicit ec: ExecutionContext): ModelClient =
    new ModelClient {

      def listModels: Future[GetModelsResponse] =
        executeRequest[GetModelsResponse](client)(
          BaseUrl + ResourcePath,
          RequestMethod.Get,
          getHeaders(config)
        )

      def retrieveModel(modelId: String): Future[Model] =
        executeRequest[Model](client)(
          BaseUrl + ResourcePath + s"/$modelId",
          RequestMethod.Get,
          getHeaders(config)
        )

    }
}
