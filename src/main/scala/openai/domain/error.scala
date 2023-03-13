package openai.domain

object error {

  final case class OpenAiApiError(message: String) extends Exception(message)

}
