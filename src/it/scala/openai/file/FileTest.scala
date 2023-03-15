package openai.file

import munit.FunSuite
import openai.{TestClient, TestLogger}
import openai.module.file.domain.UploadFileRequest

import java.io.File
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class FileTest extends FunSuite with TestClient with TestLogger {

  test("list files") {
    val futureResponse = testClient.file.list()

    futureResponse
      .map { response =>
        logger.info(s"Response: $response")
        assert(response.data.nonEmpty)
      }
  }

  test("upload file") {
    val request = UploadFileRequest(
      purpose = "fine-tune",
      file = new File("src/test/resources/test.jsonl")
    )

    val futureResponse = testClient.file.upload(request)

    futureResponse
      .map { response =>
        logger.info(s"Response: $response")
        assert(response.status.contains("uploaded"))
      }
  }

  import cats.syntax.all._

  test("upload, retrieve metadata, then delete file") {
    val uploadRequest = UploadFileRequest(
      purpose = "fine-tune",
      file = new File("src/test/resources/test.jsonl")
    )
    testClient.file.upload(uploadRequest).map { uploadResponse =>
      logger.info(s"Upload response: $uploadResponse")
      // attempt to use iterateUntilM until the response's status field contains "processed"
      false.iterateUntilM { _ =>
        Thread.sleep(5000)
        testClient.file.retrieve(uploadResponse.id).map { retrieveResponse =>
          logger.info(s"Retrieve response: $retrieveResponse")
          retrieveResponse.status.contains("processed")
        }
      }(isProcessed => isProcessed) >> testClient.file
        .delete(uploadResponse.id)
        .map { deleteResponse =>
          logger.info(s"Delete response: $deleteResponse")
          assert(deleteResponse.deleted)
        }
    }
  }

  test("upload, retrieve content, then delete file") {
    val uploadRequest = UploadFileRequest(
      purpose = "fine-tune",
      file = new File("src/test/resources/test.jsonl")
    )
    testClient.file.upload(uploadRequest).map { uploadResponse =>
      logger.info(s"Upload response: $uploadResponse")
      // attempt to use iterateUntilM until the response's status field contains "processed"
      false.iterateUntilM { _ =>
        Thread.sleep(5000)
        testClient.file.retrieve(uploadResponse.id).map { retrieveResponse =>
          logger.info(s"Retrieve response: $retrieveResponse")
          retrieveResponse.status.contains("processed")
        }
      }(isProcessed => isProcessed) >>
        testClient.file
          .retrieveContent(uploadResponse.id)
          .map { content =>
            logger.info(s"Delete response: $content")
            assert(content.fileContent.nonEmpty)
          } >>
        testClient.file
          .delete(uploadResponse.id)
          .map { deleteResponse =>
            logger.info(s"Delete response: $deleteResponse")
            assert(deleteResponse.deleted)
          }
    }
  }

}
