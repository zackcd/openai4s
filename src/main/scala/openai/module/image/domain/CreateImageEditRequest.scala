package openai.module.image.domain

import io.circe.{Encoder, derivation}
import io.circe.derivation.deriveEncoder
import openai.domain.OpenAiRequest

import java.io.File

final case class CreateImageEditRequest(
    image: File,
    mask: Option[String],
    prompt: String,
    n: Option[Int] = None,
    size: Option[ImageSize] = None,
    responseFormat: Option[ImageResponseFormat] = None,
    user: Option[String] = None
) extends OpenAiRequest {
  require(n.fold(true)(n => n >= 1 && n <= 10))
}

object CreateImageEditRequest {
  implicit val encoder: Encoder[CreateImageEditRequest] = deriveEncoder(
    derivation.renaming.snakeCase
  )
}