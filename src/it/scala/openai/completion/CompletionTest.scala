package openai.completion

import munit.FunSuite
import openai.{TestClient, TestLogger}
import openai.module.completion.domain.CreateCompletionRequest

import scala.concurrent.ExecutionContext.Implicits.global

class CompletionTest extends FunSuite with TestClient with TestLogger {

  test("ask") {
    val request = CreateCompletionRequest(
      model = "text-davinci-003",
      prompt = Some("This is a test prompt")
    )

    val futureResponse = testClient.completion.create(request)

    futureResponse
      .map { response =>
        logger.info(s"Response: $response")
        assert(response.choices.nonEmpty)
      }
  }

}
