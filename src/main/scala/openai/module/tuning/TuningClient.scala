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

/** Manage fine-tuning jobs to tailor a model to your specific training data.
  * @see
  *   https://platform.openai.com/docs/api-reference/fine-tunes
  */
trait TuningClient {

  /** Creates a job that fine-tunes a specified model from a given dataset.
    * Response includes details of the enqueued job including job status and the
    * name of the fine-tuned models once complete.
    *
    * @see
    *   https://platform.openai.com/docs/api-reference/fine-tunes/create
    * @param request
    *   The data to use for this request.
    * @return
    *   The fine-tune job and its metadata.
    */
  def create(request: CreateFineTuneRequest): Future[FineTune]

  /** List your organization's fine-tuning jobs
    * @see
    *   https://platform.openai.com/docs/api-reference/fine-tunes/list
    * @return
    *   The fine-tune jobs and their metadata.
    */
  def list(): Future[GetFineTunesResponse]

  /** Gets info about the fine-tune job.
    * @see
    *   https://platform.openai.com/docs/api-reference/fine-tunes/retrieve
    * @param fineTuneId
    *   The ID of the fine-tune job
    * @return
    *   The fine-tune job and its metadata.
    */
  def retrieve(fineTuneId: String): Future[FineTune]

  /** Immediately cancel a fine-tune job.
    * @see
    *   https://platform.openai.com/docs/api-reference/fine-tunes/cancel
    * @param fineTuneId
    *   The ID of the fine-tune job to cancel
    * @return
    *   The fine-tune job and its metadata.
    */
  def cancel(fineTuneId: String): Future[FineTune]

  /** Get fine-grained status updates for a fine-tune job. Note: the stream
    * parameter is not yet supported.
    * @see
    *   https://platform.openai.com/docs/api-reference/fine-tunes/events
    * @param fineTuneId
    *   The ID of the fine-tune job to get events for
    * @return
    *   The fine-tune job events and their metadata.
    */
  def listEvents(fineTuneId: String): Future[GetFineTuneEventsResponse]

  /** Delete a fine-tuned model. You must have the Owner role in your
    * organization.
    * @see
    *   https://platform.openai.com/docs/api-reference/fine-tunes/delete-model
    * @param model
    *   The model to delete
    * @return
    *   The deleted model and its metadata.
    */
  def deleteModel(model: String): Future[DeleteFineTuneModelResponse]

}

object TuningClient {

  private val ResourcePath = "/v1/fine-tunes"

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
