package openai.module.embedding.domain

import io.circe.{Encoder, derivation}
import io.circe.derivation.deriveEncoder
import openai.domain.OpenAiRequest

/** @see
  *   https://platform.openai.com/docs/api-reference/embeddings/create
  * @param model
  *   ID of the model to use. You can use the List models API to see all of your
  *   available models, or see our Model overview for descriptions of them.
  * @param input
  *   Input text to get embeddings for, encoded as a string or array of tokens.
  *   To get embeddings for multiple inputs in a single request, pass an array
  *   of strings or array of token arrays. Each input must not exceed 8192
  *   tokens in length.
  * @param user
  *   A unique identifier representing your end-user, which can help OpenAI to
  *   monitor and detect abuse.
  */
final case class CreateEmbeddingRequest(
    model: String,
    input: List[String],
    user: Option[String]
) extends OpenAiRequest

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
