package openai.module.file.domain

import openai.domain.OpenAiRequest
import openai.http.RequestPart
import openai.http.RequestPart.{FilePart, StringPart}

import java.io.File

/** @param file
  *   Name of the JSON Lines file to be uploaded. If the purpose is set to
  *   "fine-tune", each line is a JSON record with "prompt" and "completion"
  *   fields representing your training examples.
  * @param purpose
  *   The intended purpose of the uploaded documents. Use "fine-tune" for
  *   Fine-tuning. This allows us to validate the format of the uploaded file.
  */
final case class UploadFileRequest(
    file: File,
    purpose: String
) extends OpenAiRequest {

  def toMultipartMap: Map[String, RequestPart] = Map(
    "file" -> FilePart(this.file),
    "purpose" -> StringPart(this.purpose)
  )
}
