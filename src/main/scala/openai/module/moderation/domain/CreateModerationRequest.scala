package openai.module.moderation.domain

import io.circe.{Encoder, derivation}
import io.circe.derivation.deriveEncoder
import openai.domain.OpenAiRequest

/** @param input
  *   The input text to classify
  * @param model
  *   Two content moderations models are available: text-moderation-stable and
  *   text-moderation-latest. The default is text-moderation-latest which will
  *   be automatically upgraded over time. This ensures you are always using our
  *   most accurate model. If you use text-moderation-stable, we will provide
  *   advanced notice before updating the model. Accuracy of
  *   text-moderation-stable may be slightly lower than for
  *   text-moderation-latest.
  */
case class CreateModerationRequest(input: String, model: Option[String] = None) extends OpenAiRequest

object CreateModerationRequest {
  implicit val encoder: Encoder[CreateModerationRequest] = deriveEncoder(
    derivation.renaming.snakeCase
  )
}
