package openai.module.file

import openai.Utilities.getHeaders
import openai.http.{OpenAiHttpClient, RequestMethod}
import openai.http.OpenAiHttpClient.executeRequest
import openai.{BaseUrl, OpenAiClient, OpenAiConfig}
import openai.module.file.domain.{DeleteFileResponse, FileData, GetFilesResponse}

import scala.concurrent.{ExecutionContext, Future}

trait FileClient extends OpenAiClient {

  val ResourcePath = "/v1/files"

  def list(): Future[GetFilesResponse]

  def upload(): Future[FileData]

  def delete(fileId: String): Future[DeleteFileResponse]

  def retrieve(fileId: String): Future[FileData]

  def retrieveContent(fileId: String): Future[Unit]

}

object FileClient {

  def apply(config: OpenAiConfig, client: OpenAiHttpClient)(implicit
      ec: ExecutionContext
  ): FileClient =
    new FileClient() {

      def list(): Future[GetFilesResponse] =
        executeRequest[GetFilesResponse](client)(
          BaseUrl + ResourcePath,
          RequestMethod.Get,
          getHeaders(config)
        )

      def upload(): Future[FileData] =
        executeRequest[FileData](client)(
          BaseUrl + ResourcePath,
          RequestMethod.Get,
          getHeaders(config)
        )

      def delete(fileId: String): Future[DeleteFileResponse] =
        executeRequest[DeleteFileResponse](client)(
          BaseUrl + ResourcePath + s"/$fileId",
          RequestMethod.Delete,
          getHeaders(config)
        )

      def retrieve(fileId: String): Future[FileData] =
        executeRequest[FileData](client)(
          BaseUrl + ResourcePath + s"/$fileId",
          RequestMethod.Get,
          getHeaders(config)
        )

      def retrieveContent(fileId: String): Future[Unit] =
        executeRequest[Unit](client)(
          BaseUrl + ResourcePath + s"/$fileId/content",
          RequestMethod.Get,
          getHeaders(config)
        )

    }

}
