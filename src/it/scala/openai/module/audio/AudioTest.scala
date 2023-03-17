package openai.module.audio

import munit.FunSuite
import openai.{TestClient, TestLogger}
import openai.module.audio.domain.{
  CreateTranscriptionRequest,
  CreateTranslationRequest
}

import java.io.File
import scala.concurrent.ExecutionContext.Implicits.global

class AudioTest extends FunSuite with TestClient with TestLogger {

  test("create transcription") {
    val request = CreateTranscriptionRequest(
      file = new File("./src/it/resources/test_audio.wav"),
      model = "whisper-1",
      prompt = None
    )

    val futureResponse = testClient.audio.createTranscription(request)

    futureResponse
      .map { response =>
        logger.info(s"Response: $response")
        assert(response.text.nonEmpty)
      }
  }

  test("create translation") {
    val request = CreateTranslationRequest(
      file = new File("./src/it/resources/test_audio.wav"),
      model = "whisper-1",
      prompt = None
    )

    val futureResponse = testClient.audio.createTranslation(request)

    futureResponse
      .map { response =>
        logger.info(s"Response: $response")
        assert(response.text.nonEmpty)
      }
  }
}
