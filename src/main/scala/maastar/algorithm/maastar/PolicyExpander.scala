package maastar.algorithm.maastar

import maastar.agent.Agent
import maastar.policy.{Policy, PolicyNode}

import scala.collection.mutable


/**
 * Created by nwertzberger on 7/2/14.
 */
class PolicyExpander(
    nodeExpander: PolicyNodeExpander = new PolicyNodeExpander()
) {

    def expandPolicy(policy: Policy): Iterator[Policy] = {

        // Track the agent policies that need to be combined.
        val agents: mutable.Stack[Agent] = new mutable.Stack()
        for ((agent, policy) <- policy.agentPolicies) {
            agents.push(agent)
        }

        // Generate the first set of iterators
        val policyIterators: mutable.Map[Agent, Iterator[PolicyNode]] = new mutable.HashMap()
        for ((agent, policy) <- policy.agentPolicies) {
            policyIterators.put(agent, nodeExpander.expandPolicyNode(policy))
        }

        // Prime the policies
        val nextPolicy: mutable.Map[Agent, PolicyNode] = new mutable.HashMap()
        for ((agent, policies) <- policyIterators) {
            nextPolicy.put(agent, policies.next())
        }

        return new Iterator[Policy] {
            def hasNext() = !agents.isEmpty

            def next(): Policy = {
                val curr = nextPolicy.toMap

                val agentsToUpdate: mutable.Stack[Agent] = new mutable.Stack()

                // Track which policies have been cycled through.
                while (!agents.isEmpty && policyIterators(agents.top).isEmpty) {
                    agentsToUpdate.push(agents.pop())
                }

                if (!agents.isEmpty) {
                    agentsToUpdate.push(agents.pop())
                    while (!agentsToUpdate.isEmpty) {
                        val newAgent = agentsToUpdate.pop()
                        if (policyIterators(newAgent).isEmpty) {
                            policyIterators.put(newAgent, nodeExpander.expandPolicyNode(policy.agentPolicies(newAgent)))
                        }
                        agents.push(newAgent)
                        nextPolicy.put(newAgent, policyIterators(newAgent).next())
                    }
                }
                return new Policy(curr)
            }
        }
    }

}
