package openai.module.model.domain

import io.circe.{Decoder, derivation}
import io.circe.derivation.deriveDecoder

case class Model(
    id: String,
    `object`: String,
    created: Long,
    ownedBy: String,
    permission: List[Permission]
)

object Model {
  implicit val decoder: Decoder[Model] = deriveDecoder(
    derivation.renaming.snakeCase
  )
}

final case class Permission(
    id: String,
    `object`: String,
    created: Long,
    allowCreateEngine: Boolean,
    allowSampling: Boolean,
    allowLogprobs: Boolean,
    allowSearchIndices: Boolean,
    allowView: Boolean,
    allowFineTuning: Boolean,
    organization: String,
    group: Option[String],
    isBlocking: Boolean
)

object Permission {
  implicit val decoder: Decoder[Permission] = deriveDecoder(
    derivation.renaming.snakeCase
  )
}
