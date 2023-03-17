package openai

import scala.concurrent.ExecutionContext.Implicits.global

trait TestClient {

  private val config: OpenAiConfig = {
    OpenAiConfig(
      apiKey = sys.env("OPENAI_API_KEY"),
      organization = sys.env.get("OPENAI_ORGANIZATION")
    )
  }

  val testClient: OpenAi = OpenAi(config)
}
