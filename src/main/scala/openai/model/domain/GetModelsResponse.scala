package openai.model.domain

import io.circe.{Decoder, derivation}
import io.circe.derivation.deriveDecoder

case class GetModelsResponse(data: List[Model], `object`: String)

object GetModelsResponse {
  implicit val decoder: Decoder[GetModelsResponse] = deriveDecoder(
    derivation.renaming.snakeCase
  )
}
