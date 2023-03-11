package openai.domain

import io.circe.Encoder
import io.circe.syntax.EncoderOps
import openai.module.chat.domain.CreateChatCompletionRequest
import openai.module.image.domain._
import openai.module.completion.domain.CreateCompletionRequest
import openai.module.edit.domain.CreateEditRequest
import openai.module.embedding.domain.CreateEmbeddingRequest
import openai.module.moderation.domain.CreateModerationRequest

trait OpenAiRequest

object OpenAiRequest {
  implicit val encoder: Encoder[OpenAiRequest] = Encoder.instance {
    case r: CreateCompletionRequest     => r.asJson
    case r: CreateEditRequest           => r.asJson
    case r: CreateEmbeddingRequest      => r.asJson
    case r: CreateImageRequest          => r.asJson
    case r: CreateImageEditRequest      => r.asJson
    case r: CreateImageVariationRequest => r.asJson
    case r: CreateModerationRequest     => r.asJson
    case r: CreateChatCompletionRequest => r.asJson
  }
}
