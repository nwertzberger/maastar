package maastar.algorithm.maastar

import maastar.game.State
import maastar.heuristic.TigerGameHeuristic


/**
 * The class for the MaaStar Algorithm
 *
 * MaaStar
 */
class MaaStar(maxLayers: Int = 10,
              policyExpander: PolicyExpander = new PolicyExpander(),
              policyChooser: PolicyChooser = new PolicyChooser(
                new TigerGameHeuristic()
              ),
              policySplitter: PolicySplitter = new PolicySplitter()) {

  def calculatePolicy(initialBelief: Map[State, Double]): Policy = {
    var bestPolicy = new Policy()

    var openPolicies = policyExpander.expandPolicyNodes(bestPolicy)

    while (!openPolicies.isEmpty) {
      val candidate = policyChooser.findBestPolicy(openPolicies, initialBelief)
      val children = policyExpander.expandPolicyNodes(candidate)
      val (completePolicies, incompletePolicies) = policySplitter.splitOnDepth(children, maxLayers)

      bestPolicy = policyChooser.findBestPolicy(
        completePolicies ++ Set(bestPolicy),
        initialBelief
      )

      openPolicies = policySplitter
        .filter(openPolicies ++ incompletePolicies, initialBelief)
        .estimatedValuesBelow(bestPolicy.getValue())
    }
    return bestPolicy
  }
}