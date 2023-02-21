package openai.completion

import munit.FunSuite
import openai.{OpenAi, TestClient}
import openai.completion.domain.CreateCompletionRequest

import scala.concurrent.ExecutionContext.Implicits.global

class CompletionTest extends FunSuite with TestClient {

  test("ask") {
    val request = CreateCompletionRequest(
      model = "text-davinci-003",
      prompt = Some("This is a test prompt")
    )

    testClient.completion.ask(request).map(println)
  }

}
