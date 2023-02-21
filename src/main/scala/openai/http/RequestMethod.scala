package openai.http

sealed trait RequestMethod

object RequestMethod {
  case object Get extends RequestMethod
  case object Post extends RequestMethod
  case object PostMultipart extends RequestMethod
  case object Delete extends RequestMethod
  case object Put extends RequestMethod
}
