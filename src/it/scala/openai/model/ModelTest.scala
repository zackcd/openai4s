package openai.model

import munit.FunSuite
import openai.{TestClient, TestLogger}

import scala.concurrent.ExecutionContext.Implicits.global

class ModelTest extends FunSuite with TestClient with TestLogger {

  test("get models") {
    val futureResponse = testClient.model.list()

    futureResponse
      .map { response =>
        logger.info(s"Response: $response")
        assert(response.data.nonEmpty)
      }
  }

  test("get model") {
    val modelId = "text-davinci-003"
    val futureResponse = testClient.model.retrieve(modelId)

    futureResponse
      .map { response =>
        logger.info(s"Response: $response")
        assert(response.id == modelId)
      }
  }

}
