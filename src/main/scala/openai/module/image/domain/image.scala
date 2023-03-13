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
      Decoder[UrlImageData].widen,
      Decoder[JsonImageData].widen
    ).reduceLeft(_ or _)
}

final case class UrlImageData(url: String) extends ImageData

object UrlImageData {
  implicit val decoder: Decoder[UrlImageData] = deriveDecoder(
    derivation.renaming.snakeCase
  )
}

final case class JsonImageData(b64Json: String) extends ImageData

object JsonImageData {
  implicit val decoder: Decoder[JsonImageData] = deriveDecoder(
    derivation.renaming.snakeCase
  )
}
