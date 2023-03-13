package openai.module.moderation.domain

import io.circe.{Decoder, derivation}
import io.circe.derivation.deriveDecoder

case class CreateModerationResponse(
    id: String,
    model: String,
    results: List[Moderation]
)

object CreateModerationResponse {
  implicit val encoder: Decoder[CreateModerationResponse] = deriveDecoder(
    derivation.renaming.snakeCase
  )
}
