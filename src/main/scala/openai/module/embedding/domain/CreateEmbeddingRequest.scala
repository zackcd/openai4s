package openai.module.embedding.domain

import io.circe.{Encoder, derivation}
import io.circe.derivation.deriveEncoder
import openai.domain.OpenAiRequest

final case class CreateEmbeddingRequest(
    model: String,
    input: List[String],
    user: Option[String]
) extends OpenAiRequest {}

object CreateEmbeddingRequest {

  def apply(
      model: String,
      input: String,
      user: Option[String] = None
  ): CreateEmbeddingRequest =
    CreateEmbeddingRequest(model, List(input), user)

  implicit val encoder: Encoder[CreateEmbeddingRequest] = deriveEncoder(
    derivation.renaming.snakeCase
  )
}
