package openai.module.embedding

import cats.syntax.all._
import openai.Utilities.getHeaders
import openai.{BaseUrl, OpenAiClient, OpenAiConfig}
import openai.module.embedding.domain.{CreateEmbeddingRequest, Embedding}
import openai.http.{OpenAiHttpClient, RequestMethod}
import openai.http.OpenAiHttpClient.executeRequest

import scala.concurrent.{ExecutionContext, Future}

/** Get a vector representation of a given input that can be easily consumed by
  * machine learning models and algorithms.
  * @see
  *   https://platform.openai.com/docs/api-reference/embeddings
  */
sealed trait EmbeddingClient extends OpenAiClient {

  val ResourcePath = "/v1/embeddings"

  /** Creates an embedding vector representing the input text.
    * @see
    *   https://platform.openai.com/docs/api-reference/embeddings/create
    * @param request
    *   The data to use for this request.
    * @return
    *   The embedding and its metadata.
    */
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
