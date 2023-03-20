package openai.module.audio

import openai.Utilities.getHeaders
import openai.{BaseUrl, OpenAiConfig}
import openai.http.{OpenAiHttpClient, RequestMethod}
import openai.http.OpenAiHttpClient.executeMultipartRequest
import openai.module.audio.domain.{
  AudioResponse,
  CreateTranscriptionRequest,
  CreateTranslationRequest
}

import scala.concurrent.{ExecutionContext, Future}

/** Learn how to turn audio into text.
  * @see
  *   https://platform.openai.com/docs/api-reference/audio
  */
sealed trait AudioClient {

  /** Transcribes audio into the input language.
    * @see
    *   https://platform.openai.com/docs/api-reference/audio/create
    * @param request
    *   The data to use for this request
    * @return
    *   The transcription
    */
  def createTranscription(
      request: CreateTranscriptionRequest
  ): Future[AudioResponse]

  /** Translates audio into into English.
    * @see
    *   https://platform.openai.com/docs/api-reference/audio/create
    * @param request
    *   The data to use for this request
    * @return
    */
  def createTranslation(
      request: CreateTranslationRequest
  ): Future[AudioResponse]
}

object AudioClient {

  private val ResourcePath = "/v1/audio"

  def apply(config: OpenAiConfig, client: OpenAiHttpClient)(implicit
      ec: ExecutionContext
  ): AudioClient = new AudioClient {

    def createTranscription(
        request: CreateTranscriptionRequest
    ): Future[AudioResponse] =
      executeMultipartRequest[AudioResponse](client)(
        BaseUrl + ResourcePath + "/transcriptions",
        RequestMethod.Post,
        getHeaders(config),
        request.toMultipartMap
      )

    def createTranslation(
        request: CreateTranslationRequest
    ): Future[AudioResponse] =
      executeMultipartRequest[AudioResponse](client)(
        BaseUrl + ResourcePath + "/translations",
        RequestMethod.Post,
        getHeaders(config),
        request.toMultipartMap
      )

  }

}
