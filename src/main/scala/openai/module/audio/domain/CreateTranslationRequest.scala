package openai.module.audio.domain

import openai.domain.OpenAiRequest
import openai.http.RequestPart
import openai.http.RequestPart.{DoublePart, FilePart, StringPart}

import java.io.File

/** @see
  *   https://platform.openai.com/docs/api-reference/audio/create
  * @param file
  *   The audio file to transcribe, in one of these formats: mp3, mp4, mpeg,
  *   mpga, m4a, wav, or webm.
  * @param model
  *   ID of the model to use. Only whisper-1 is currently available.
  * @param prompt
  *   An optional text to guide the model's style or continue a previous audio
  *   segment. The prompt should match the audio language.
  * @param responseFormat
  *   The format of the transcript output, in one of these options: json, text,
  *   srt, verbose_json, or vtt. Defaults to json.
  * @param temperature
  *   The sampling temperature, between 0 and 1. Higher values like 0.8 will
  *   make the output more random, while lower values like 0.2 will make it more
  *   focused and deterministic. If set to 0, the model will use log probability
  *   to automatically increase the temperature until certain thresholds are
  *   hit. Defaults to 0.
  */
final case class CreateTranslationRequest(
    file: File,
    model: String,
    prompt: Option[String] = None,
    responseFormat: Option[ResponseFormat] = None,
    temperature: Option[Double] = None
) extends OpenAiRequest {

  def toMultipartMap: Map[String, RequestPart] = Map(
    "file" -> FilePart(this.file),
    "model" -> StringPart(this.model)
  ) ++
    this.prompt.map("prompt" -> StringPart(_)).toMap ++
    this.responseFormat
      .map(rs => "response_format" -> StringPart(rs.value))
      .toMap ++
    this.temperature.map("temperature" -> DoublePart(_)).toMap
}
