package openai

import org.http4s.Header
import org.typelevel.ci.CIStringSyntax

object Utilities {

  def getHeaders(config: OpenAiConfig): List[Header.Raw] =
    List(
      Header.Raw(ci"Content-Type", "application/json"),
      Header.Raw(ci"Authorization", s"Bearer ${config.apiKey}")
    ) ++ config.organization.fold(List[Header.Raw]())(org =>
      List(Header.Raw(ci"OpenAI-Organization", org))
    )

}
