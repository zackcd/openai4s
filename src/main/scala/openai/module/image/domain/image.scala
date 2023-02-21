package openai.module.image.domain

import cats.syntax.functor._
import io.circe.{Decoder, derivation}
import io.circe.derivation.deriveDecoder

final case class Image(
    created: Long,
    data: List[ImageData]
)

object Image {
  implicit val decoder: Decoder[Image] = deriveDecoder(
    derivation.renaming.snakeCase
  )
}

sealed trait ImageData

object ImageData {
  implicit val decodeEvent: Decoder[ImageData] =
    List[Decoder[ImageData]](
      Decoder[CreateImageUrlData].widen,
      Decoder[CreateImageJsonData].widen
    ).reduceLeft(_ or _)
}

final case class CreateImageUrlData(url: String) extends ImageData

object CreateImageUrlData {
  implicit val decoder: Decoder[CreateImageUrlData] = deriveDecoder(
    derivation.renaming.snakeCase
  )
}

final case class CreateImageJsonData(b64Json: String) extends ImageData

object CreateImageJsonData {
  implicit val decoder: Decoder[CreateImageJsonData] = deriveDecoder(
    derivation.renaming.snakeCase
  )
}
