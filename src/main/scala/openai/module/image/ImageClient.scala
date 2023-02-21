package openai.module.image

import cats.syntax.all._
import openai.Utilities.getHeaders
import openai.{BaseUrl, OpenAiClient, OpenAiConfig}
import openai.http.{OpenAiHttpClient, RequestMethod}
import openai.http.OpenAiHttpClient.executeRequest
import openai.module.image.domain._

import scala.concurrent.{ExecutionContext, Future}

sealed trait ImageClient extends OpenAiClient {

  val ResourcePath = "/v1/images"

  def create(request: CreateImageRequest): Future[Image]

  def createEdit(
      request: CreateImageEditRequest
  ): Future[Image]

  def createVariation(
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
          request: CreateImageEditRequest
      ): Future[Image] =
        executeRequest[Image](client)(
          BaseUrl + ResourcePath + "/edits",
          RequestMethod.Post,
          getHeaders(config),
          request.some
        )

      def createVariation(
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
