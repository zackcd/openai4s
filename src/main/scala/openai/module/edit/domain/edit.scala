package openai.module.edit.domain

import io.circe.derivation.deriveDecoder
import io.circe.{Decoder, derivation}
import openai.module.domain.Usage

final case class Edit(
    `object`: String,
    created: Long,
    choices: List[EditChoice],
    usage: Usage
)

object Edit {
  implicit val decoder: Decoder[Edit] = deriveDecoder(
    derivation.renaming.snakeCase
  )
}

final case class EditChoice(
    text: String,
    index: Int
)

object EditChoice {
  implicit val decoder: Decoder[EditChoice] = deriveDecoder(
    derivation.renaming.snakeCase
  )
}
