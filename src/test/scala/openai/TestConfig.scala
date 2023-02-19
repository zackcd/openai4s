package openai

import java.net.http.HttpClient

trait TestConfig {

  val config: OpenAiConfig =
    OpenAiConfig(
      apiKey = sys.env("OPENAI_API_KEY"),
      organization = None,
      mode = "sync"
    )

  val httpClient: HttpClient = HttpClient.newBuilder().build()

}
