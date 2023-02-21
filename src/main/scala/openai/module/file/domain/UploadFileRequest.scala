package openai.module.file.domain

import io.circe.{Encoder, derivation}
import io.circe.derivation.deriveEncoder
import openai.domain.OpenAiRequest

import java.io.File

case class UploadFileRequest(
    file: File,
    purpose: String
) extends OpenAiRequest

object UploadFileRequest {
  implicit val encoder: Encoder[UploadFileRequest] = deriveEncoder(
    derivation.renaming.snakeCase
  )
}
