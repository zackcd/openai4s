package openai.module.completion.domain

import io.circe.derivation.deriveDecoder
import io.circe.{Decoder, derivation}
import openai.domain.Usage

final case class Completion(
    id: String,
    `object`: String,
    created: Long,
    model: String,
    choices: List[CompletionChoice],
    usage: Usage
)

object Completion {
  implicit val decoder: Decoder[Completion] = deriveDecoder(
    derivation.renaming.snakeCase
  )
}

final case class CompletionChoice(
    text: String,
    index: Int,
    logprobs: Option[Map[String, Double]],
    finishReason: String
)

object CompletionChoice {
  implicit val decoder: Decoder[CompletionChoice] = deriveDecoder(
    derivation.renaming.snakeCase
  )
}
