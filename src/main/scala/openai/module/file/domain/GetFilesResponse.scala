package openai.module.file.domain

import io.circe.{Decoder, derivation}
import io.circe.derivation.deriveDecoder

final case class GetFilesResponse(data: List[FileData], `object`: String)

object GetFilesResponse {
  implicit val decoder: Decoder[GetFilesResponse] = deriveDecoder(
    derivation.renaming.snakeCase
  )
}
