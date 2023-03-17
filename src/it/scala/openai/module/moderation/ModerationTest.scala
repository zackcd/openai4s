package openai.module.moderation

import munit.FunSuite
import openai.{TestClient, TestLogger}
import openai.module.moderation.domain.CreateModerationRequest

import scala.concurrent.ExecutionContext.Implicits.global

class ModerationTest extends FunSuite with TestClient with TestLogger {

  test("Create moderation test 1") {
    val req = CreateModerationRequest("I want to kill them.")
    val futureResponse = testClient.moderation.create(req)

    futureResponse
      .map { response =>
        logger.info(s"Response: $response")
        assert(response.results.nonEmpty)
      }
  }

}
