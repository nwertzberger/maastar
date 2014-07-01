package maastar.algorithm.maastar

import maastar.game.State
import maastar.policy.{PolicyValueOrdering, Policy}

import scala.collection.mutable.PriorityQueue


/**
 * The class for the MaaStar Algorithm
 *
 * MaaStar
 */
class MaaStar(
               maxDepth: Int = 10,
               policyEvaluator: PolicyEvaluator = new PolicyEvaluator(),
               policyExpander: PolicyExpander = new PolicyExpander(),
               policySplitter: PolicySplitter = new PolicySplitter()) {


  def calculatePolicy(initialBelief: Map[State, Double]): Policy = {
    val openPolicies = new PriorityQueue[Policy]()(PolicyValueOrdering)
    while (!openPolicies.isEmpty) {
      val candidate = openPolicies.dequeue()
      if (candidate.depth() >= maxDepth)
        return candidate

      /*
      val children = policyExpander
        .expandPolicyNodes(candidate)
        .toSet
      */

      //children.foreach(c => openPolicies.enqueue(c))
    }
    throw new Exception("candidate policy blew up")
  }
}