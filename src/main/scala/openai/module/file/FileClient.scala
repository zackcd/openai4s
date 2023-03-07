package openai.module.file

import openai.Utilities.getHeaders
import openai.http.{OpenAiHttpClient, RequestMethod}
import openai.http.OpenAiHttpClient.{executeMultipartRequest, executeRequest}
import openai.http.RequestPart.{FilePart, StringPart}
import openai.{BaseUrl, OpenAiClient, OpenAiConfig}
import openai.module.file.domain.{
  DeleteFileResponse,
  FileData,
  GetFileContentResponse,
  GetFilesResponse,
  UploadFileRequest
}

import scala.concurrent.{ExecutionContext, Future}

sealed trait FileClient extends OpenAiClient {

  val ResourcePath = "/v1/files"

  def list(): Future[GetFilesResponse]

  /** Upload a file that contains document(s) to be used across various
    * endpoints/features. Currently, the size of all the files uploaded by one
    * organization can be up to 1 GB. Please contact us if you need to increase
    * the storage limit.
    * @param request
    *   The data to use for this request
    * @return
    *   The uploaded file's metadata
    */
  def upload(request: UploadFileRequest): Future[FileData]

  /** Delete a file.
    * @param fileId
    *   The ID of the file to use for this request
    * @return
    *   The deleted file's metadata
    */
  def delete(fileId: String): Future[DeleteFileResponse]

  /** Returns information about a specific file.
    * @param fileId
    *   The ID of the file to use for this request
    * @return
    *   A file's metadata
    */
  def retrieve(fileId: String): Future[FileData]

  /** Returns the contents of the specified file
    * @param fileId
    *   The ID of the file to use for this request
    * @return
    *   A file
    */
  def retrieveContent(fileId: String): Future[GetFileContentResponse]

}

object FileClient {

  def apply(config: OpenAiConfig, client: OpenAiHttpClient)(implicit
      ec: ExecutionContext
  ): FileClient =
    new FileClient {

      private val PurposeMultipartKey = "purpose"
      private val FileMultipartKey = "file"

      def list(): Future[GetFilesResponse] =
        executeRequest[GetFilesResponse](client)(
          BaseUrl + ResourcePath,
          RequestMethod.Get,
          getHeaders(config)
        )

      def upload(request: UploadFileRequest): Future[FileData] =
        executeMultipartRequest[FileData](client)(
          BaseUrl + ResourcePath,
          RequestMethod.Post,
          getHeaders(config),
          Map(
            PurposeMultipartKey -> StringPart(request.purpose),
            FileMultipartKey -> FilePart(request.file)
          )
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

      def retrieveContent(fileId: String): Future[GetFileContentResponse] =
        executeRequest[GetFileContentResponse](client)(
          BaseUrl + ResourcePath + s"/$fileId/content",
          RequestMethod.Get,
          getHeaders(config)
        )

    }

}
