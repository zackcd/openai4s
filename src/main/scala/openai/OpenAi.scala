package openai

import openai.completion.CompletionClient
import openai.edit.EditClient
import openai.embedding.EmbeddingClient
import openai.image.ImageClient
import openai.model.ModelClient
import openai.tuning.TuningClient

import scala.concurrent.ExecutionContext

case class OpenAi(config: OpenAiConfig)(implicit ec: ExecutionContext) {

//  val completion: CompletionClient = CompletionClient(config)
//
//  val edit: EditClient = ???
//
//  val embedding: EmbeddingClient = ???
//
//  val image: ImageClient = ???

  val model: ModelClient = ModelClient(config)

//  val tuning: TuningClient = ???

}

object OpenAi {

  def apply(config: OpenAiConfig)(implicit ec: ExecutionContext): OpenAi =
    new OpenAi(config) {}

}
