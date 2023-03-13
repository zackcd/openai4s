package openai.module.audio.domain

import io.circe.Encoder

sealed trait ResponseFormat {
  def value: String
}

object ResponseFormat {

  final case object Json extends ResponseFormat {
    final val value = "json"
  }

  final case object Text extends ResponseFormat {
    final val value = "text"
  }

  final case object Srt extends ResponseFormat {
    final val value = "srt"
  }

  final case object VerboseJson extends ResponseFormat {
    final val value = "verbose_json"
  }

  final case object Vtt extends ResponseFormat {
    final val value = "vtt"
  }

  implicit val encoder: Encoder[ResponseFormat] =
    Encoder.encodeString.contramap[ResponseFormat](_.value)

}
