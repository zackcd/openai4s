package openai.module.chat.domain

import io.circe.{Encoder, derivation}
import io.circe.derivation.deriveEncoder
import openai.domain.OpenAiRequest

/** TODO: introduce support for stream
  * @see
  *   https://platform.openai.com/docs/api-reference/chat/create
  * @param model
  *   ID of the model to use. Currently, only gpt-3.5-turbo and
  *   gpt-3.5-turbo-0301 are supported.
  * @param messages
  *   The messages to generate chat completions for, in the chat format.
  * @param temperature
  *   What sampling temperature to use, between 0 and 2. Higher values like 0.8
  *   will make the output more random, while lower values like 0.2 will make it
  *   more focused and deterministic. We generally recommend altering this or
  *   top_p but not both. Default value: 1.
  * @param topP
  *   An alternative to sampling with temperature, called nucleus sampling,
  *   where the model considers the results of the tokens with top_p probability
  *   mass. So 0.1 means only the tokens comprising the top 10% probability mass
  *   are considered. We generally recommend altering this or temperature but
  *   not both. Default value: 1.
  * @param n
  *   How many chat completion choices to generate for each input message.
  *   Default value: 1.
  * @param stop
  *   Up to 4 sequences where the API will stop generating further tokens.
  * @param maxTokens
  *   The maximum number of tokens allowed for the generated answer. By default,
  *   the number of tokens the model can return will be (4096 - prompt tokens).
  * @param presencePenalty
  *   Number between -2.0 and 2.0. Positive values penalize new tokens based on
  *   whether they appear in the text so far, increasing the model's likelihood
  *   to talk about new topics. Default value: 0.
  * @param frequencyPenalty
  *   Number between -2.0 and 2.0. Positive values penalize new tokens based on
  *   their existing frequency in the text so far, decreasing the model's
  *   likelihood to repeat the same line verbatim. Default value: 0.
  * @param logitBias
  *   Modify the likelihood of specified tokens appearing in the completion.
  *
  * Accepts a json object that maps tokens (specified by their token ID in the
  * tokenizer) to an associated bias value from -100 to 100. Mathematically, the
  * bias is added to the logits generated by the model prior to sampling. The
  * exact effect will vary per model, but values between -1 and 1 should
  * decrease or increase likelihood of selection; values like -100 or 100 should
  * result in a ban or exclusive selection of the relevant token.
  * @param user
  *   unique identifier representing your end-user, which can help OpenAI to
  *   monitor and detect abuse.
  */
final case class CreateChatCompletionRequest(
    model: String,
    messages: List[String],
    temperature: Option[Double],
    topP: Option[Double],
    n: Option[Int],
    stop: Option[List[String]],
    maxTokens: Option[Int],
    presencePenalty: Option[Double],
    frequencyPenalty: Option[Double],
    logitBias: Option[Map[String, Double]],
    user: Option[String]
) extends OpenAiRequest

object CreateChatCompletionRequest {
  implicit val encoder: Encoder[CreateChatCompletionRequest] = deriveEncoder(
    derivation.renaming.snakeCase
  )
}
