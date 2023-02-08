package openai.completion.domain

import io.circe.derivation.deriveDecoder
import io.circe.{Decoder, derivation}

case class CreateCompletionResponse(
    id: String,
    `object`: String,
    created: Long,
    model: String,
    choices: List[Choice],
    usage: Usage
)

object CreateCompletionResponse {
  implicit val decoder: Decoder[CreateCompletionResponse] = deriveDecoder(
    derivation.renaming.snakeCase
  )
}

case class Choice(
    text: String,
    index: Int,
    logprobs: Option[Map[String, Double]],
    finishReason: String
)

object Choice {
  implicit val decoder: Decoder[Choice] = deriveDecoder(
    derivation.renaming.snakeCase
  )
}

case class Usage(promptTokens: Int, completionTokens: Int, totalTokens: Int)

object Usage {
  implicit val decoder: Decoder[Usage] = deriveDecoder(
    derivation.renaming.snakeCase
  )
}
