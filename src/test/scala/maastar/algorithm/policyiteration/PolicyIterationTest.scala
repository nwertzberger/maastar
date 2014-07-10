package maastar.algorithm.policyiteration

import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, ShouldMatchers}

/**
 * Created by nwertzberger on 7/7/14.
 */
class PolicyIterationTest extends FlatSpec with ShouldMatchers with MockFactory {
  "PolicyIteration" should "start with an arbitrary policy" in {
    // Markov decision processes are based on the "markov assumption", which is that
    // decisions are based only on the current state.
    val iteration = new PolicyIteration()


  }
}
