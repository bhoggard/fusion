package example

import org.scalatest._

class FusionServerSpec extends FlatSpec with Matchers {
  "The Hello object" should "say hello" in {
    "hello" shouldEqual "hello"
  }
}
