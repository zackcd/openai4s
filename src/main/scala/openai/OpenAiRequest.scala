package openai

import io.circe.Encoder
import io.circe.syntax.EncoderOps
import openai.completion.domain.CreateCompletionRequest

trait OpenAiRequest

object OpenAiRequest {
  implicit val encoder: Encoder[OpenAiRequest] = Encoder.instance {
    case r: CreateCompletionRequest => r.asJson
    case r: CreateCompletionRequest => r.asJson
  }
}
