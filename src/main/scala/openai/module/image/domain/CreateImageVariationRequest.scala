package openai.module.image.domain

import io.circe.{Encoder, derivation}
import io.circe.derivation.deriveEncoder
import openai.domain.OpenAiRequest
import openai.http.RequestPart

import java.io.File

/** @see
  *   https://platform.openai.com/docs/api-reference/images/create-variation
  * @param image
  *   The image to use as the basis for the variation(s). Must be a valid PNG
  *   file, less than 4MB, and square.
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
final case class CreateImageVariationRequest(
    image: File,
    n: Option[Int] = None,
    size: Option[ImageSize] = None,
    responseFormat: Option[ImageResponseFormat] = None,
    user: Option[String] = None
) extends OpenAiRequest {

  def toMultipartMap: Map[String, RequestPart] = Map(
    "image" -> RequestPart.FilePart(image)
  ) ++
    this.n.map("n" -> RequestPart.IntPart(_)).toMap ++
    this.size.map(s => "size" -> RequestPart.StringPart(s.value)).toMap ++
    this.responseFormat
      .map(rf => "response_format" -> RequestPart.StringPart(rf.value))
      .toMap ++
    this.user.map("user" -> RequestPart.StringPart(_)).toMap

}
