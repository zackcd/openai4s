package openai.module.file.domain

import openai.domain.OpenAiRequest

case class UploadFileRequest(
    purpose: String
) extends OpenAiRequest
