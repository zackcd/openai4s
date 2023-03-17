package openai.module.embedding

import munit.FunSuite
import openai.{TestClient, TestLogger}
import openai.module.embedding.domain.CreateEmbeddingRequest

import scala.concurrent.ExecutionContext.Implicits.global

class EmbeddingTest extends FunSuite with TestClient with TestLogger {

  test("create embedding") {
    val request = CreateEmbeddingRequest(
      model = "text-embedding-ada-002",
      input = "The food was delicious and the waiter..."
    )

    val futureResponse = testClient.embedding.create(request)

    futureResponse
      .map { response =>
        logger.info(s"Response: $response")
        assert(response.data.nonEmpty)
      }
  }

}
