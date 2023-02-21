package openai

import scala.concurrent.ExecutionContext.Implicits.global

trait TestClient {

  private val config: OpenAiConfig =
    OpenAiConfig(
      apiKey = sys.env("OPENAI_API_KEY"),
      organization = None,
      mode = "sync"
    )

  val testClient: OpenAi = OpenAi(config)
}
