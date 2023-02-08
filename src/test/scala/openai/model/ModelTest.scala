package openai.model

import munit.CatsEffectSuite
import openai.TestConfig

class ModelTest extends CatsEffectSuite with TestConfig {

  test("get models") {
    ModelClient(config).getAll.map(println)
  }

  test("get model") {
    val modelId = "text-davinci-003"
    ModelClient(config).getById(modelId).map(println)
  }

}
