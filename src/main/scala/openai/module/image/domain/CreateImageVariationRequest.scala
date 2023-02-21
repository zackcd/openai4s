package openai.module.image.domain

import io.circe.{Encoder, derivation}
import io.circe.derivation.deriveEncoder
import openai.domain.OpenAiRequest

import java.io.File

final case class CreateImageVariationRequest(
    n: Option[Int] = None,
    size: Option[ImageSize] = None,
    responseFormat: Option[ImageResponseFormat] = None,
    user: Option[String] = None
) extends OpenAiRequest {
  require(n.fold(true)(n => n >= 1 && n <= 10))
}

object CreateImageVariationRequest {
  implicit val encoder: Encoder[CreateImageVariationRequest] = deriveEncoder(
    derivation.renaming.snakeCase
  )
}
