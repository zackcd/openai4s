package openai.embedding

import munit.FunSuite
import openai.TestClient
import openai.embedding.domain.CreateEmbeddingRequest

import scala.concurrent.ExecutionContext.Implicits.global

class EmbeddingTest extends FunSuite with TestClient {

  test("create embedding") {
    val request = CreateEmbeddingRequest(
      model = "text-embedding-ada-002",
      input = "The food was delicious and the waiter..."
    )

    testClient.embedding.create(request).map(println)
  }

}
