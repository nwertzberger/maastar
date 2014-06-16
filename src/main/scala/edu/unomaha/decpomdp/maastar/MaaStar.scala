package edu.unomaha.decpomdp.maastar

import edu.unomaha.decpomdp.maastar.game.State


/**
 * The class for the MaaStar Algorithm
 *
 * MaaStar
 */
class MaaStar(
    initialBelief: Map[State, Float],
    maxLayers: Int = 10,
    policyExpander: PolicyExpander = new PolicyExpander(),
    policyChooser: PolicyChooser = new PolicyChooser(),
    policySplitter: PolicySplitter = new PolicySplitter(),
    heuristic : Heuristic = new MdpHeuristic()) {

  def calculatePolicy() : Policy = {
    var bestPolicy = new Policy()

    var openPolicies = policyExpander.expandPolicyNodes()
    
    while(!openPolicies.isEmpty) {
      val candidate = policyChooser.findBestPolicy(openPolicies, heuristic, initialBelief)
      val children = policyExpander.expandPolicyNodes(candidate)
      val (completePolicies,
          incompletePolicies
          ) = policySplitter.splitOnDepth(children, maxLayers)

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