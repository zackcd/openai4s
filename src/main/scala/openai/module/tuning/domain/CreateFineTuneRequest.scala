package openai.module.tuning.domain

import io.circe.{Encoder, derivation}
import io.circe.derivation.deriveEncoder
import openai.domain.OpenAiRequest

final case class CreateFineTuneRequest(
    trainingFile: String,
    validationFile: Option[String],
    model: Option[String],
    nEpochs: Option[Int],
    batchSize: Option[Int],
    learningRateMultiplier: Option[Double],
    promptLossWeight: Option[Double],
    computeClassificationMetrics: Option[Boolean],
    classificationNClasses: Option[Int],
    classificationPositiveClass: Option[String],
    classificationBetas: Option[List[Double]],
    suffix: Option[String]
) extends OpenAiRequest {
  require(
    batchSize.fold(true)(bs => bs > 0 && bs <= 256),
    "batchSize must be between 1 and 256"
  )

  require(
    suffix.fold(true)(s => s.length <= 40),
    "suffix may be a maximum of 40 characters"
  )
}

object CreateFineTuneRequest {
  implicit val encoder: Encoder[CreateFineTuneRequest] = deriveEncoder(
    derivation.renaming.snakeCase
  )
}
