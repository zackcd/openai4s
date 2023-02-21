package openai.module.embedding.domain

import io.circe.{Decoder, derivation}
import io.circe.derivation.deriveDecoder
import openai.module.domain.Usage

final case class Embedding(
    `object`: String,
    data: List[EmbeddingData],
    model: String,
    usage: Usage
)

object Embedding {
  implicit val decoder: Decoder[Embedding] = deriveDecoder(
    derivation.renaming.snakeCase
  )
}

final case class EmbeddingData(
    `object`: String,
    embedding: List[Double],
    index: Int
)

object EmbeddingData {
  implicit val decoder: Decoder[EmbeddingData] = deriveDecoder(
    derivation.renaming.snakeCase
  )
}
