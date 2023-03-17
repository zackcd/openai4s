package openai

object Utilities {

  def getHeaders(config: OpenAiConfig): Map[String, String] =
    Map(
      "Authorization" -> s"Bearer ${config.apiKey}"
    ) ++ config.organization.fold(Map[String, String]())(org =>
      Map("OpenAI-Organization" -> org)
    )

}
