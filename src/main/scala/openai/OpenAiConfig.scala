package openai

final case class OpenAiConfig(
    apiKey: String,
    organization: Option[String],
    mode: String
)
