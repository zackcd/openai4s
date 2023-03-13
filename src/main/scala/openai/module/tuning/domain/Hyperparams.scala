package openai.module.tuning.domain

import io.circe.{Decoder, derivation}
import io.circe.derivation.deriveDecoder

final case class Hyperparams(
    batchSize: Int,
    learningRateMultiplier: Double,
    nEpochs: Int,
    promptLossWeight: Double
)

object Hyperparams {
  implicit val decoder: Decoder[Hyperparams] = deriveDecoder(
    derivation.renaming.snakeCase
  )
}
