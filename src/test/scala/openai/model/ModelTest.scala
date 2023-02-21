package openai.model

import munit.FunSuite
import openai.TestClient

import scala.concurrent.ExecutionContext.Implicits.global

class ModelTest extends FunSuite with TestClient {

  test("get models") {
    testClient.model.getAll.map(println)
  }

  test("get model") {
    val modelId = "text-davinci-003"
    testClient.model.getById(modelId).map(println)
  }

}
