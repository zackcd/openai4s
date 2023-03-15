package openai

import com.typesafe.scalalogging.Logger

trait TestLogger {

  val logger: Logger = Logger("TestLogger")

}
