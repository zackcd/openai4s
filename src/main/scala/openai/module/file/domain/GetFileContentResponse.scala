package openai.module.file.domain

import io.circe.Decoder

final case class GetFileContentResponse(fileContent: String)

// TODO: finish this
object GetFileContentResponse {
  implicit val decoder: Decoder[GetFileContentResponse] =
    Decoder.decodeJson.map(ds => {
      println("-----")
      println(ds)
      println("-----")
      GetFileContentResponse(ds.noSpaces)
    })
}
