package openai.domain

import io.circe.{Decoder, derivation}
import io.circe.derivation.deriveDecoder

final case class Usage(
    promptTokens: Int,
    completionTokens: Option[Int],
    totalTokens: Int
)

object Usage {
  implicit val decoder: Decoder[Usage] = deriveDecoder(
    derivation.renaming.snakeCase
  )
}
