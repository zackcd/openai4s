package openai.chat.domain

import io.circe.generic.JsonCodec

import java.util.UUID

@JsonCodec
case class ChatAskRequest(
    action: String,
    messages: List[Message],
    conversation_id: String,
    parent_message_id: String = UUID.randomUUID().toString,
    model: String = "text-davinci-002-render"
)

@JsonCodec
case class Message(id: String, role: String, content: Content)

@JsonCodec
case class Content(content_type: String, parts: List[String])
