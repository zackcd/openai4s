package openai.module.tuning.domain

import io.circe.{Decoder, derivation}
import io.circe.derivation.deriveDecoder

final case class FineTuneFile(
    id: String,
    `object`: String,
    bytes: Long,
    created_at: Long,
    filename: String,
    purpose: String
)

object FineTuneFile {
  implicit val decoder: Decoder[FineTuneFile] = deriveDecoder(
    derivation.renaming.snakeCase
  )
}
