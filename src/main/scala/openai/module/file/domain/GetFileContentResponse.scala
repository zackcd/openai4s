package openai.module.file.domain

final case class GetFileContentResponse(fileContent: String)

object GetFileContentResponse {
  implicit val fac: String => GetFileContentResponse = GetFileContentResponse(_)
}
