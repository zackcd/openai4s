package openai

import openai.completion.CompletionClient

import java.net.URI
import java.net.http.HttpResponse.BodyHandlers
import java.net.http.{HttpClient, HttpRequest}

sealed trait OpenAi {

  val completion: CompletionClient

}

object OpenAi {

  def apply(config: OpenAiConfig): OpenAi = new OpenAi {

    val completion: CompletionClient = CompletionClient(config)

  }

  val client: HttpClient = HttpClient.newBuilder().build()

  val request: HttpRequest = HttpRequest
    .newBuilder()
    .uri(URI.create("http://openjdk.org/"))
    .build()

  val res = client.send(request, BodyHandlers.ofString())

}
