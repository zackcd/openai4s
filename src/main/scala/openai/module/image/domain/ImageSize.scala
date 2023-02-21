package openai.module.image.domain

import io.circe.Encoder

sealed trait ImageSize {

  def value: String
}

object ImageSize {

  final case object Small extends ImageSize {
    final val value = "256x256"
  }

  final case object Medium extends ImageSize {
    final val value = "512x512"
  }

  final case object Large extends ImageSize {
    final val value = "1024x1024"
  }

  implicit val encoder: Encoder[ImageSize] =
    Encoder.encodeString.contramap[ImageSize](_.value)
}
