package edu.unomaha.decpomdp.maastar

/**
 * The class for the MaaStar Algorithm
 *
 * MaaStar
 */
class MaaStar(
    initialBelief: Belief,
    maxLayers: Int = 10,
    policyExpander: PolicyExpander = new PolicyExpander(),
    policyChooser: PolicyChooser = new PolicyChooser()) {

  def calculatePolicy() = {
    var bestPolicyValue = Float.NegativeInfinity
    var bestPolicy = null

    var openPolicies = policyExpander.expandPolicyNodes()
    
    while(!openPolicies.isEmpty) {
      val candidate = policyChooser.findBestPolicy(openPolicies, initialBelief)
      val children = policyExpander.expandPolicyNodes(candidate)
      
      
    }

  }

}