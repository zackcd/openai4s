package openai.module.tuning

import cats.syntax.all._
import openai.Utilities.getHeaders
import openai.{BaseUrl, OpenAiConfig}
import openai.http.{OpenAiHttpClient, RequestMethod}
import openai.http.OpenAiHttpClient.executeRequest
import openai.module.tuning.domain.{
  CreateFineTuneRequest,
  DeleteFineTuneModelResponse,
  FineTune,
  GetFineTuneEventsResponse,
  GetFineTunesResponse
}

import scala.concurrent.{ExecutionContext, Future}

trait TuningClient {

  val ResourcePath = "/v1/fine-tunes"

  def create(request: CreateFineTuneRequest): Future[FineTune]

  def list(): Future[GetFineTunesResponse]

  def retrieve(fineTuneId: String): Future[FineTune]

  def cancel(fineTuneId: String): Future[FineTune]

  def listEvents(fineTuneId: String): Future[GetFineTuneEventsResponse]

  def deleteModel(model: String): Future[DeleteFineTuneModelResponse]

}

object TuningClient {

  def apply(config: OpenAiConfig, client: OpenAiHttpClient)(implicit
      ec: ExecutionContext
  ): TuningClient = new TuningClient() {

    def create(request: CreateFineTuneRequest): Future[FineTune] =
      executeRequest[FineTune](client)(
        BaseUrl + ResourcePath,
        RequestMethod.Post,
        getHeaders(config),
        request.some
      )

    def list(): Future[GetFineTunesResponse] =
      executeRequest[GetFineTunesResponse](client)(
        BaseUrl + ResourcePath,
        RequestMethod.Get,
        getHeaders(config)
      )

    def retrieve(fineTuneId: String): Future[FineTune] =
      executeRequest[FineTune](client)(
        BaseUrl + ResourcePath + s"/$fineTuneId",
        RequestMethod.Get,
        getHeaders(config)
      )

    def cancel(fineTuneId: String): Future[FineTune] =
      executeRequest[FineTune](client)(
        BaseUrl + ResourcePath + s"/$fineTuneId/cancel",
        RequestMethod.Post,
        getHeaders(config)
      )

    def listEvents(fineTuneId: String): Future[GetFineTuneEventsResponse] =
      executeRequest[GetFineTuneEventsResponse](client)(
        BaseUrl + ResourcePath + s"/$fineTuneId/events",
        RequestMethod.Get,
        getHeaders(config)
      )

    def deleteModel(model: String): Future[DeleteFineTuneModelResponse] =
      executeRequest[DeleteFineTuneModelResponse](client)(
        BaseUrl + s"/v1/models/$model",
        RequestMethod.Delete,
        getHeaders(config)
      )

  }

}
