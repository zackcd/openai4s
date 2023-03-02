package openai.module.file.domain

import io.circe.{Encoder, derivation}
import io.circe.derivation.deriveEncoder
import openai.domain.OpenAiRequest

case class UploadFileRequest(
    purpose: String
) extends OpenAiRequest

object UploadFileRequest {
  implicit val encoder: Encoder[UploadFileRequest] = deriveEncoder(
    derivation.renaming.snakeCase
  )
}
