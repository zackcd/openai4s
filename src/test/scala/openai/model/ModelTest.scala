package openai.model

import munit.FunSuite
import openai.TestConfig

import scala.concurrent.ExecutionContext.Implicits.global

class ModelTest extends FunSuite with TestConfig {

  test("get models") {
    ModelClient(config).getAll.map(println)
  }

  test("get model") {
    val modelId = "text-davinci-003"
    ModelClient(config).getById(modelId).map(println)
  }

}
