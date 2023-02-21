package openai.module.tuning.domain

import io.circe.{Decoder, derivation}
import io.circe.derivation.deriveDecoder

final case class FineTune(
    id: String,
    `object`: String,
    model: String,
    createdAt: Long,
    events: List[FineTuneEvent],
    fineTunedModel: Option[String],
    hyperparams: Hyperparams,
    organizationId: Option[String],
    resultFiles: List[FineTuneFile],
    status: String,
    validationFiles: List[FineTuneFile],
    trainingFiles: List[FineTuneFile],
    updated_at: Long
)

object FineTune {
  implicit val decoder: Decoder[FineTune] = deriveDecoder(
    derivation.renaming.snakeCase
  )
}
