package openai.module.edit

import munit.FunSuite
import openai.{TestClient, TestLogger}
import openai.module.edit.domain.CreateEditRequest

import scala.concurrent.ExecutionContext.Implicits.global

class EditTest extends FunSuite with TestClient with TestLogger {

  test("edit") {
    val request = CreateEditRequest(
      model = "text-davinci-edit-001",
      input = Some("Hello this is a test.It haz a few errors."),
      instruction = "Fix and grammar and spelling errors."
    )

    val futureResponse = testClient.edit.create(request)

    futureResponse
      .map { response =>
        logger.info(s"Response: $response")
        assert(response.choices.nonEmpty)
      }
  }

}
