package openai.module.image

import munit.FunSuite
import openai.{TestClient, TestLogger}
import openai.module.image.domain._

import java.io.File
import scala.concurrent.ExecutionContext.Implicits.global

class ImageTest extends FunSuite with TestClient with TestLogger {

  test("create image url response") {
    val n = 3
    val request = CreateImageRequest(
      prompt = "A camel drinking from an oasis.",
      n = Some(n),
      size = Some(ImageSize.Medium),
      responseFormat = Some(ImageResponseFormat.url)
    )

    val futureResponse = testClient.image.create(request)

    futureResponse
      .map { response =>
        logger.info(s"Response: $response")
        assert(response.data.length == n)
      }
  }

  test("create image json response") {
    val n = 3
    val request = CreateImageRequest(
      prompt = "A camel drinking from an oasis.",
      n = Some(3),
      size = Some(ImageSize.Medium),
      responseFormat = Some(ImageResponseFormat.json)
    )

    val futureResponse = testClient.image.create(request)

    futureResponse
      .map { response =>
        assert(response.data.length == n)
      }
  }

  test("create image edit url response") {
    val image = new File("src/it/resources/test_image.png")
    val n = 3
    val request = CreateImageEditRequest(
      image = image,
      mask = None,
      prompt = "A camel drinking from an oasis.",
      n = Some(n),
      size = Some(ImageSize.Medium),
      responseFormat = Some(ImageResponseFormat.url)
    )

    val futureResponse = testClient.image.createEdit(request)

    futureResponse
      .map { response =>
        logger.info(s"Response: $response")
        assert(response.data.length == n)
      }
  }

  test("create image edit json response") {
    val image = new File("src/it/resources/test_image.png")
    val n = 3
    val request = CreateImageEditRequest(
      image = image,
      mask = None,
      prompt = "A camel drinking from an oasis.",
      n = Some(n),
      size = Some(ImageSize.Medium),
      responseFormat = Some(ImageResponseFormat.json)
    )

    val futureResponse = testClient.image.createEdit(request)

    futureResponse
      .map { response =>
        assert(response.data.length == n)
      }
  }

  test("create image variation url response") {
    val image = new File("src/it/resources/test_image.png")
    val n = 3
    val request = CreateImageVariationRequest(
      image = image,
      n = Some(n),
      size = Some(ImageSize.Medium),
      responseFormat = Some(ImageResponseFormat.url)
    )

    val futureResponse = testClient.image.createVariation(request)

    futureResponse
      .map { response =>
        logger.info(s"Response: $response")
        assert(response.data.length == n)
      }
  }

  test("create image variation json response") {
    val image = new File("src/it/resources/test_image.png")
    val n = 3
    val request = CreateImageVariationRequest(
      image = image,
      n = Some(n),
      size = Some(ImageSize.Medium),
      responseFormat = Some(ImageResponseFormat.json)
    )

    val futureResponse = testClient.image.createVariation(request)

    futureResponse
      .map { response =>
        assert(response.data.length == n)
      }
  }

}
