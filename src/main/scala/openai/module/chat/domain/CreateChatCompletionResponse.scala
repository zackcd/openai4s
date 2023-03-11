package openai.module.chat.domain

import io.circe.{Decoder, derivation}
import io.circe.derivation.deriveDecoder
import openai.domain.Usage

final case class CreateChatCompletionResponse(
    id: String,
    `object`: String,
    created: Long,
    choices: List[ChatCompletionChoice],
    usage: Usage
)

object CreateChatCompletionResponse {
  implicit val decoder: Decoder[CreateChatCompletionResponse] = deriveDecoder(
    derivation.renaming.snakeCase
  )
}

final case class ChatCompletionChoice(
    index: Int,
    message: ChatCompletionMessage,
    finishReason: String
)

object ChatCompletionChoice {
  implicit val decoder: Decoder[ChatCompletionChoice] = deriveDecoder(
    derivation.renaming.snakeCase
  )
}

final case class ChatCompletionMessage(role: String, content: String)

object ChatCompletionMessage {
  implicit val decoder: Decoder[ChatCompletionMessage] = deriveDecoder(
    derivation.renaming.snakeCase
  )
}
