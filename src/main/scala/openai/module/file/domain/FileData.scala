package openai.module.file.domain

import io.circe.{Decoder, derivation}
import io.circe.derivation.deriveDecoder

final case class FileData(
    id: String,
    `object`: String,
    bytes: Int,
    createdAt: Long,
    filename: String,
    purpose: String
)

object FileData {
  implicit val decoder: Decoder[FileData] = deriveDecoder(
    derivation.renaming.snakeCase
  )
}
