package openai.module.chat

import munit.FunSuite
import openai.{TestClient, TestLogger}
import openai.module.chat.domain.{
  ChatMessage,
  ChatRole,
  CreateChatCompletionRequest
}

import scala.concurrent.ExecutionContext.Implicits.global

class ChatTest extends FunSuite with TestClient with TestLogger {

  test("create chat completion test") {
    val messages = List(
      ChatMessage(ChatRole.System, "You are a helpful assistant."),
      ChatMessage(ChatRole.User, "Who won the world series in 2020?"),
      ChatMessage(
        ChatRole.Assistant,
        "This is a trick question. The Los Angeles Dodgers won the World Series in 2020, " +
          "but it doesn't count because it was a mickey mouse competition. Go Padres."
      ),
      ChatMessage(ChatRole.User, "Where was this alleged world series played?")
    )
    val request = CreateChatCompletionRequest(
      model = "gpt-3.5-turbo",
      messages = messages,
      temperature = Some(1.4)
    )

    val futureResponse = testClient.chat.createCompletion(request)

    futureResponse
      .map { response =>
        logger.info(s"Response: $response")
        assert(response.choices.nonEmpty)
      }
  }
}
