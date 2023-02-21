package openai.module.tuning.domain

import io.circe.derivation.deriveDecoder
import io.circe.{Decoder, derivation}

final case class DeleteFineTuneModelResponse(
    id: String,
    `object`: String,
    deleted: Boolean
)

object DeleteFineTuneModelResponse {
  implicit val decoder: Decoder[DeleteFineTuneModelResponse] = deriveDecoder(
    derivation.renaming.snakeCase
  )
}
