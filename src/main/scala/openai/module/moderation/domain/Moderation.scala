package openai.module.moderation.domain

import io.circe.{Decoder, derivation}
import io.circe.derivation.deriveDecoder

case class Moderation(
    categories: Map[String, Boolean],
    categoryScores: Map[String, Double],
    flagged: Boolean
)

object Moderation {
  implicit val encoder: Decoder[Moderation] = deriveDecoder(
    derivation.renaming.snakeCase
  )
}
