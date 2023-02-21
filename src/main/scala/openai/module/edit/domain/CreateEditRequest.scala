package openai.module.edit.domain

import io.circe.{Encoder, derivation}
import io.circe.derivation.deriveEncoder
import openai.domain.OpenAiRequest

final case class CreateEditRequest(
    model: String,
    input: Option[String],
    instruction: String,
    n: Option[Int] = None,
    temperature: Option[Double] = None,
    topP: Option[Double] = None
) extends OpenAiRequest {
  private val validModels =
    Set("text-davinci-edit-001", "code-davinci-edit-001")
  require(validModels.contains(model), s"Invalid model: $model")

  require(
    temperature.fold(true)(temp => temp >= 0 && temp <= 2),
    "temperature must be between 0 and 1"
  )
}

object CreateEditRequest {
  implicit val encoder: Encoder[CreateEditRequest] = deriveEncoder(
    derivation.renaming.snakeCase
  )
}
