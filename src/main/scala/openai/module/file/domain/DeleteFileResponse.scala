package openai.module.file.domain

import io.circe.{Decoder, derivation}
import io.circe.derivation.deriveDecoder

final case class DeleteFileResponse(id: String, `object`: String, deleted: Boolean)

object DeleteFileResponse {
  implicit val decoder: Decoder[DeleteFileResponse] = deriveDecoder(
    derivation.renaming.snakeCase
  )
}
