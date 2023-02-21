package openai.module.completion.domain

import io.circe.{Encoder, derivation}
import io.circe.derivation.deriveEncoder
import openai.domain.OpenAiRequest

final case class CreateCompletionRequest(
    model: String,
    prompt: Option[String],
    suffix: Option[String] = None,
    maxTokens: Option[Int] = None,
    temperature: Option[Double] = None,
    topP: Option[Double] = None,
    n: Option[Int] = None,
    stream: Option[Boolean] = None,
    logprobs: Option[Int] = None,
    echo: Option[Boolean] = None,
    stop: Option[List[String]] = None,
    presencePenalty: Option[Double] = None,
    frequencyPenalty: Option[Double] = None,
    bestOf: Option[Int] = None,
    logitBias: Option[Map[String, Double]] = None,
    user: Option[String] = None
) extends OpenAiRequest {
  require(
    logprobs.fold(true)(lp => lp <= 5),
    "The maximum value for logprobs is 5"
  )

  require(
    presencePenalty.fold(true)(pp => pp > -2 && pp < 2),
    "presencePenalty must be a number between -2.0 and 2.0"
  )

  require(
    frequencyPenalty.fold(true)(fp => fp > -2 && fp < 2),
    "frequencyPenalty must be a number between -2.0 and 2.0"
  )

  require(
    logitBias.fold(true)(lb => lb.values.forall(v => v >= -100 && v <= 100)),
    "logitBias values must be from -100 to 100"
  )
}

object CreateCompletionRequest {
  implicit val encoder: Encoder[CreateCompletionRequest] = deriveEncoder(
    derivation.renaming.snakeCase
  )
}
