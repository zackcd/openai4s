package openai.module.image.domain

import io.circe.Encoder

sealed trait ImageResponseFormat {
  def value: String
}

object ImageResponseFormat {
  final case object url extends ImageResponseFormat {
    final val value = "url"
  }

  final case object json extends ImageResponseFormat {
    final val value = "b64_json"
  }

  implicit val encoder: Encoder[ImageResponseFormat] =
    Encoder.encodeString.contramap[ImageResponseFormat](_.value)
}
