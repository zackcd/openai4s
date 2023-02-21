package openai.module.tuning.domain

import io.circe.{Decoder, derivation}
import io.circe.derivation.deriveDecoder

final case class GetFineTuneEventsResponse(
    `object`: String,
    data: List[FineTuneEvent]
)

object GetFineTuneEventsResponse {
  implicit val decoder: Decoder[GetFineTuneEventsResponse] = deriveDecoder(
    derivation.renaming.snakeCase
  )
}
