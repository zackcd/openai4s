package openai.module.image.domain

import io.circe.derivation.deriveEncoder
import io.circe.{Encoder, derivation}
import openai.domain.OpenAiRequest

/** @see
  *   https://platform.openai.com/docs/api-reference/images/create
  * @param prompt
  *   A text description of the desired image(s). The maximum length is 1000
  *   characters.
  * @param n
  *   The number of images to generate. Must be between 1 and 10. Defaults to 1.
  * @param size
  *   The size of the generated images. Must be one of 256x256, 512x512, or
  *   1024x1024. Defaults to 1024x1024.
  * @param responseFormat
  *   The format in which the generated images are returned. Must be one of url
  *   or b64_json. Defaults to url.
  * @param user
  *   A unique identifier representing your end-user, which can help OpenAI to
  *   monitor and detect abuse.
  */
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
