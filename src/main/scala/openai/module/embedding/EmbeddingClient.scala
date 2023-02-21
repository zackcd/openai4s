package openai.module.embedding

import cats.syntax.all._
import openai.Utilities.getHeaders
import openai.{BaseUrl, OpenAiClient, OpenAiConfig}
import openai.module.embedding.domain.{
  CreateEmbeddingRequest,
  Embedding
}
import openai.http.{OpenAiHttpClient, RequestMethod}
import openai.http.OpenAiHttpClient.executeRequest

import scala.concurrent.{ExecutionContext, Future}

sealed trait EmbeddingClient extends OpenAiClient {

  val ResourcePath = "/v1/embeddings"

  def create(request: CreateEmbeddingRequest): Future[Embedding]
}

object EmbeddingClient {

  def apply(config: OpenAiConfig, client: OpenAiHttpClient)(implicit
      ec: ExecutionContext
  ): EmbeddingClient =
    new EmbeddingClient {

      def create(
          request: CreateEmbeddingRequest
      ): Future[Embedding] =
        executeRequest[Embedding](client)(
          BaseUrl + ResourcePath,
          RequestMethod.Post,
          getHeaders(config),
          request.some
        )

    }
}
