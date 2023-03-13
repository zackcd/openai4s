package openai.module.audio.domain

import io.circe.{Decoder, derivation}
import io.circe.derivation.deriveDecoder

final case class AudioResponse(text: String)

object AudioResponse {
  implicit val decoder: Decoder[AudioResponse] = deriveDecoder(
    derivation.renaming.snakeCase
  )
}
