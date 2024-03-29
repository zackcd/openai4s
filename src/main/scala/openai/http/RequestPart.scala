package openai.http

import java.io.File

sealed trait RequestPart

object RequestPart {
  case class FilePart(part: File) extends RequestPart
  case class StringPart(part: String) extends RequestPart

  case class IntPart(part: Int) extends RequestPart

  case class DoublePart(part: Double) extends RequestPart
}
