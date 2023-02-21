package openai.edit

import munit.FunSuite
import openai.TestClient
import openai.edit.domain.CreateEditRequest

import scala.concurrent.ExecutionContext.Implicits.global

class EditTest extends FunSuite with TestClient {

  test("edit") {
    val request = CreateEditRequest(
      model = "text-davinci-edit-001",
      input = Some("Hello this is a test.It haz a few errors."),
      instruction = "Fix and grammar and spelling errors."
    )

    testClient.edit.create(request).map(println)
  }



}
