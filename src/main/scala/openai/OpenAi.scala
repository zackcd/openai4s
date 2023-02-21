package openai

import openai.http.OpenAiHttpClient.DefaultHttpClient
import openai.http.OpenAiHttpClient
import openai.module.image.ImageClient
import openai.module.completion.CompletionClient
import openai.module.edit.EditClient
import openai.module.embedding.EmbeddingClient
import openai.module.file.FileClient
import openai.module.model.ModelClient
import openai.module.tuning.TuningClient

import scala.concurrent.ExecutionContext

final case class OpenAi(config: OpenAiConfig)(implicit ec: ExecutionContext) {

  private val httpClient: OpenAiHttpClient = DefaultHttpClient()

  lazy val completion: CompletionClient = CompletionClient(config, httpClient)

  lazy val edit: EditClient = EditClient(config, httpClient)

  lazy val embedding: EmbeddingClient = EmbeddingClient(config, httpClient)

  lazy val image: ImageClient = ImageClient(config, httpClient)

  lazy val model: ModelClient = ModelClient(config, httpClient)

  lazy val tuning: TuningClient = TuningClient(config, httpClient)

  lazy val file: FileClient = ???

}

object OpenAi {

  def apply(config: OpenAiConfig)(implicit ec: ExecutionContext): OpenAi =
    new OpenAi(config)

}