package openai.module.tuning.domain

import io.circe.{Encoder, derivation}
import io.circe.derivation.deriveEncoder
import openai.domain.OpenAiRequest

/** @see
  *   https://platform.openai.com/docs/api-reference/fine-tunes/create
  * @param trainingFile
  *   The ID of an uploaded file that contains training data. See upload file
  *   for how to upload a file. Your dataset must be formatted as a JSONL file,
  *   where each training example is a JSON object with the keys "prompt" and
  *   "completion". Additionally, you must upload your file with the purpose
  *   fine-tune. See the fine-tuning guide for more details.
  * @param validationFile
  *   The ID of an uploaded file that contains validation data. If you provide
  *   this file, the data is used to generate validation metrics periodically
  *   during fine-tuning. These metrics can be viewed in the fine-tuning results
  *   file. Your train and validation data should be mutually exclusive. Your
  *   dataset must be formatted as a JSONL file, where each validation example
  *   is a JSON object with the keys "prompt" and "completion". Additionally,
  *   you must upload your file with the purpose fine-tune. See the fine-tuning
  *   guide for more details.
  * @param model
  *   The name of the base model to fine-tune. You can select one of "ada",
  *   "babbage", "curie", "davinci", or a fine-tuned model created after
  *   2022-04-21. To learn more about these models, see the Models
  *   documentation. Defaults to "curie".
  * @param nEpochs
  *   The number of epochs to train the model for. An epoch refers to one full
  *   cycle through the training dataset. Defaults to 4.
  * @param batchSize
  *   The batch size to use for training. The batch size is the number of
  *   training examples used to train a single forward and backward pass. By
  *   default, the batch size will be dynamically configured to be ~0.2% of the
  *   number of examples in the training set, capped at 256 - in general, we've
  *   found that larger batch sizes tend to work better for larger datasets.
  * @param learningRateMultiplier
  *   The learning rate multiplier to use for training. The fine-tuning learning
  *   rate is the original learning rate used for pretraining multiplied by this
  *   value. By default, the learning rate multiplier is the 0.05, 0.1, or 0.2
  *   depending on final batch_size (larger learning rates tend to perform
  *   better with larger batch sizes). We recommend experimenting with values in
  *   the range 0.02 to 0.2 to see what produces the best results.
  * @param promptLossWeight
  *   The weight to use for loss on the prompt tokens. This controls how much
  *   the model tries to learn to generate the prompt (as compared to the
  *   completion which always has a weight of 1.0), and can add a stabilizing
  *   effect to training when completions are short. If prompts are extremely
  *   long (relative to completions), it may make sense to reduce this weight so
  *   as to avoid over-prioritizing learning the prompt. Defaults to 0.01.
  * @param computeClassificationMetrics
  *   If set, we calculate classification-specific metrics such as accuracy and
  *   F-1 score using the validation set at the end of every epoch. These
  *   metrics can be viewed in the results file. In order to compute
  *   classification metrics, you must provide a validation_file. Additionally,
  *   you must specify classification_n_classes for multiclass classification or
  *   classification_positive_class for binary classification. Defaults to
  *   false.
  * @param classificationNClasses
  *   The number of classes in a classification task. This parameter is required
  *   for multiclass classification.
  * @param classificationPositiveClass
  *   The positive class in binary classification. This parameter is needed to
  *   generate precision, recall, and F1 metrics when doing binary
  *   classification.
  * @param classificationBetas
  *   If this is provided, we calculate F-beta scores at the specified beta
  *   values. The F-beta score is a generalization of F-1 score. This is only
  *   used for binary classification. With a beta of 1 (i.e. the F-1 score),
  *   precision and recall are given the same weight. A larger beta score puts
  *   more weight on recall and less on precision. A smaller beta score puts
  *   more weight on precision and less on recall.
  * @param suffix
  *   A string of up to 40 characters that will be added to your fine-tuned
  *   model name. For example, a suffix of "custom-model-name" would produce a
  *   model name like ada:ft-your-org:custom-model-name-2022-02-15-04-21-04.
  */
final case class CreateFineTuneRequest(
    trainingFile: String,
    validationFile: Option[String],
    model: Option[String],
    nEpochs: Option[Int],
    batchSize: Option[Int],
    learningRateMultiplier: Option[Double],
    promptLossWeight: Option[Double],
    computeClassificationMetrics: Option[Boolean],
    classificationNClasses: Option[Int],
    classificationPositiveClass: Option[String],
    classificationBetas: Option[List[Double]],
    suffix: Option[String]
) extends OpenAiRequest

object CreateFineTuneRequest {
  implicit val encoder: Encoder[CreateFineTuneRequest] = deriveEncoder(
    derivation.renaming.snakeCase
  )
}
