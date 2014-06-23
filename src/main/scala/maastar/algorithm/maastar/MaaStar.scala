package maastar.algorithm.maastar

import maastar.game.State
import maastar.heuristic.{Heuristic, TigerGameHeuristic}


/**
 * The class for the MaaStar Algorithm
 *
 * MaaStar
 */
class MaaStar(
               maxLayers: Int = 10,
               policyExpander: PolicyExpander = new PolicyExpander(),
               policyChooser: PolicyChooser = new PolicyChooser(),
               policySplitter: PolicySplitter = new PolicySplitter(),
               heuristic: Heuristic = new TigerGameHeuristic()) {


  def calculatePolicy(initialBelief: Map[State, Float]): Policy = {
    var bestPolicy = new Policy()

    var openPolicies = policyExpander.expandPolicyNodes(bestPolicy)

    while (!openPolicies.isEmpty) {
      val candidate = policyChooser
        .findBestPolicy(openPolicies, heuristic, initialBelief)
      val children = policyExpander
        .expandPolicyNodes(candidate)
      val (completePolicies, incompletePolicies) = policySplitter.splitOnDepth(children, maxLayers)

      bestPolicy = policyChooser.findBestPolicy(
        completePolicies ++ Set(bestPolicy),
        heuristic,
        initialBelief
      )

      openPolicies = policySplitter
        .filter(openPolicies ++ incompletePolicies, initialBelief)
        .estimatedValuesBelow(bestPolicy.getValue(), heuristic)
    }
    return bestPolicy
  }
}