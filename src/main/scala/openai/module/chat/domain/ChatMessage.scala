package openai.module.chat.domain

import io.circe.{Decoder, Encoder, derivation}
import io.circe.derivation.{deriveDecoder, deriveEncoder}

final case class ChatMessage(role: ChatRole, content: String)

object ChatMessage {
  implicit val encoder: Encoder[ChatMessage] = deriveEncoder(
    derivation.renaming.snakeCase
  )

  implicit val decoder: Decoder[ChatMessage] = deriveDecoder(
    derivation.renaming.snakeCase
  )
}
