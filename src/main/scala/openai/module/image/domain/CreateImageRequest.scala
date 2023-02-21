package openai.module.image.domain

import io.circe.derivation.deriveEncoder
import io.circe.{Encoder, derivation}
import openai.domain.OpenAiRequest

final case class CreateImageRequest(
    prompt: String,
    n: Option[Int] = None,
    size: Option[ImageSize] = None,
    responseFormat: Option[ImageResponseFormat] = None,
    user: Option[String] = None
) extends OpenAiRequest {
  require(n.fold(true)(n => n >= 1 && n <= 10))
}

object CreateImageRequest {
  implicit val encoder: Encoder[CreateImageRequest] = deriveEncoder(
    derivation.renaming.snakeCase
  )
}
