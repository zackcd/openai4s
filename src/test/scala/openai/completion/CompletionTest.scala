package openai.completion

import munit.FunSuite
import openai.TestConfig
import openai.completion.domain.CreateCompletionRequest

import scala.concurrent.ExecutionContext.Implicits.global

class CompletionTest extends FunSuite with TestConfig {

  test("ask") {
    val request = CreateCompletionRequest(
      model = "text-davinci-003",
      prompt = Some("This is a test prompt")
    )
    CompletionClient(config).ask(request).map(println)
  }

}
