package openai.image

import io.circe.syntax.EncoderOps
import munit.FunSuite
import openai.TestClient
import openai.image.domain._

import scala.concurrent.ExecutionContext.Implicits.global

class ImageTest extends FunSuite with TestClient {

  test("create image url response") {
    val request = CreateImageRequest(
      prompt = "A camel drinking from an oasis.",
      n = Some(3),
      size = Some(ImageSize.Medium),
      responseFormat = Some(ImageResponseFormat.url)
    )

    testClient.image.create(request).map(println)
  }

  test("create image json response") {
    val request = CreateImageRequest(
      prompt = "A camel drinking from an oasis.",
      n = Some(3),
      size = Some(ImageSize.Medium),
      responseFormat = Some(ImageResponseFormat.json)
    )

    testClient.image.create(request).map(println)
  }

  test("create image edit url response") {
    val request = CreateImageEditRequest(
      image = "",
      mask = None,
      prompt = "A camel drinking from an oasis.",
      n = Some(3),
      size = Some(ImageSize.Medium),
      responseFormat = Some(ImageResponseFormat.url)
    )

    testClient.image.createEdit(request).map(println)
  }

  test("create image edit json response") {
    val request = CreateImageEditRequest(
      image = "",
      mask = None,
      prompt = "A camel drinking from an oasis.",
      n = Some(3),
      size = Some(ImageSize.Medium),
      responseFormat = Some(ImageResponseFormat.json)
    )

    testClient.image.createEdit(request).map(println)
  }

  test("create image variation url response") {
    val request = CreateImageVariationRequest(
      image = "",
      n = Some(3),
      size = Some(ImageSize.Medium),
      responseFormat = Some(ImageResponseFormat.url)
    )

    testClient.image.createVariation(request).map(println)
  }

  test("create image variation json response") {
    val request = CreateImageVariationRequest(
      image = "",
      n = Some(3),
      size = Some(ImageSize.Medium),
      responseFormat = Some(ImageResponseFormat.json)
    )

    testClient.image.createVariation(request).map(println)
  }

}
