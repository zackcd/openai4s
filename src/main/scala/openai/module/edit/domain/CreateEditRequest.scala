package openai.module.edit.domain

import io.circe.{Encoder, derivation}
import io.circe.derivation.deriveEncoder
import openai.domain.OpenAiRequest

/** @see
  *   https://platform.openai.com/docs/api-reference/edits/create
  * @param model
  *   ID of the model to use. You can use the text-davinci-edit-001 or
  *   code-davinci-edit-001 model with this endpoint.
  * @param input
  *   The input text to use as a starting point for the edit. Defaults to an
  *   empty string.
  * @param instruction
  *   The instruction that tells the model how to edit the prompt.
  * @param n
  *   How many edits to generate for the input and instruction. Defaults to 1.
  * @param temperature
  *   What sampling temperature to use, between 0 and 2. Higher values like 0.8
  *   will make the output more random, while lower values like 0.2 will make it
  *   more focused and deterministic. We generally recommend altering this or
  *   top_p but not both. Defaults to 1.
  * @param topP
  *   An alternative to sampling with temperature, called nucleus sampling,
  *   where the model considers the results of the tokens with top_p probability
  *   mass. So 0.1 means only the tokens comprising the top 10% probability mass
  *   are considered. We generally recommend altering this or temperature but
  *   not both. Defaults to 1.
  */
final case class CreateEditRequest(
    model: String,
    input: Option[String],
    instruction: String,
    n: Option[Int] = None,
    temperature: Option[Double] = None,
    topP: Option[Double] = None
) extends OpenAiRequest

object CreateEditRequest {
  implicit val encoder: Encoder[CreateEditRequest] = deriveEncoder(
    derivation.renaming.snakeCase
  )
}
