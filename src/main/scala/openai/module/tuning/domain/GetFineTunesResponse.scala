package openai.module.tuning.domain

import io.circe.{Decoder, derivation}
import io.circe.derivation.deriveDecoder

final case class GetFineTunesResponse(`object`: String, data: List[FineTune])

object GetFineTunesResponse {
  implicit val decoder: Decoder[GetFineTunesResponse] = deriveDecoder(
    derivation.renaming.snakeCase
  )
}
