package openai.module.chat.domain

import io.circe.{Decoder, Encoder}

sealed trait ChatRole {
  def value: String
}

object ChatRole {

  case object System extends ChatRole {
    final def value: String = "system"
  }

  case object User extends ChatRole {
    def value: String = "user"
  }

  case object Assistant extends ChatRole {
    def value: String = "assistant"
  }

  implicit val encoder: Encoder[ChatRole] =
    Encoder[String].contramap[ChatRole](_.value)
  implicit val decoder: Decoder[ChatRole] = Decoder[String].emap {
    case "system"    => Right(System)
    case "user"      => Right(User)
    case "assistant" => Right(Assistant)
    case other       => Left(s"Invalid ChatRole: $other")
  }

}
