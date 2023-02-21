package openai.module.tuning.domain

import io.circe.{Decoder, derivation}
import io.circe.derivation.deriveDecoder

final case class FineTuneEvent(
    `object`: String,
    createdAt: Long,
    level: String,
    message: String
)

object FineTuneEvent {
  implicit val decoder: Decoder[FineTuneEvent] = deriveDecoder(
    derivation.renaming.snakeCase
  )
}
