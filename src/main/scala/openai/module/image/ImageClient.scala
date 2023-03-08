package openai.module.image

import cats.syntax.all._
import openai.Utilities.getHeaders
import openai.{BaseUrl, OpenAiClient, OpenAiConfig}
import openai.http.{OpenAiHttpClient, RequestMethod}
import openai.http.OpenAiHttpClient.executeRequest
import openai.module.image.domain._

import java.io.File
import scala.concurrent.{ExecutionContext, Future}

/** Given a prompt and/or an input image, the model will generate a new image.
  * @see
  *   https://platform.openai.com/docs/api-reference/images
  */
sealed trait ImageClient extends OpenAiClient {

  val ResourcePath = "/v1/images"

  /** Creates an image given a prompt.
    * @see
    *   https://platform.openai.com/docs/api-reference/images/create
    * @param request
    *   The data to use for this request.
    * @return
    *   The image and its metadata.
    */
  def create(request: CreateImageRequest): Future[Image]

  /** Creates an edited or extended image given an original image and a prompt.
    * @see
    *   https://platform.openai.com/docs/api-reference/images/create-edit
    * @param image
    *   The image to use as the basis for the variation(s). Must be a valid PNG
    *   file, less than 4MB, and square.
    * @param request
    *   The data to use for this request.
    * @return
    *   The image and its metadata.
    */
  def createEdit(
      image: File,
      request: CreateImageEditRequest
  ): Future[Image]

  /** Creates a variation of a given image.
    * @see
    *   https://platform.openai.com/docs/api-reference/images/create-variation
    * @param image
    *   The image to use as the basis for the variation(s). Must be a valid PNG
    *   file, less than 4MB, and square.
    * @param request
    *   The data to use for this request.
    * @return
    *   The image and its metadata.
    */
  def createVariation(
      image: File,
      request: CreateImageVariationRequest
  ): Future[Image]

}

object ImageClient {

  def apply(config: OpenAiConfig, client: OpenAiHttpClient)(implicit
      ec: ExecutionContext
  ): ImageClient =
    new ImageClient {

      def create(request: CreateImageRequest): Future[Image] =
        executeRequest[Image](client)(
          BaseUrl + ResourcePath + "/generations",
          RequestMethod.Post,
          getHeaders(config),
          request.some
        )

      def createEdit(
          image: File,
          request: CreateImageEditRequest
      ): Future[Image] =
        executeRequest[Image](client)(
          BaseUrl + ResourcePath + "/edits",
          RequestMethod.Post,
          getHeaders(config),
          request.some
        )

      def createVariation(
          image: File,
          request: CreateImageVariationRequest
      ): Future[Image] =
        executeRequest[Image](client)(
          BaseUrl + ResourcePath + "/variations",
          RequestMethod.Post,
          getHeaders(config),
          request.some
        )

    }
}
