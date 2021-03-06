package maastar.algorithm.maastar

import maastar.game.{Action, Observation}
import maastar.policy.PolicyNode

import scala.collection.mutable


class PolicyNodeExpander(
    allPossibleActions: Set[Action] = Set(),
    allPossibleObservations: Set[Observation] = Set()
) {

    val observationCombos = (0 to allPossibleObservations.size).map(
        len => allPossibleObservations.subsets(len).toSet
    ).toSet.flatten

    val allObservationPolicies = actionObservationPermutations(
        observationCombos,
        allPossibleActions.map(act => new PolicyNode(act))
    )

    def expandPolicyNode(policy: PolicyNode): Iterator[PolicyNode] = {
        val targetPolicy = policy.createClone()
        val policyStack = generateNodePolicyStack(targetPolicy)

        return new Iterator[PolicyNode] {
            def hasNext() = !policyStack.isEmpty

            def next(): PolicyNode = {
                val currPolicy = targetPolicy.createClone()
                // Determine next policy
                val finishedNodes = getFinishedNodes(policyStack)

                if (!policyStack.isEmpty) {
                    finishedNodes.push(policyStack.pop())
                    while (!finishedNodes.isEmpty) {
                        val (node, childIterator) = getNextNode(finishedNodes.pop())
                        node.setTransitions(childIterator.next())
                        policyStack.push((node, childIterator))
                    }
                }
                return currPolicy
            }
        }
    }

    private def getFinishedNodes(
        policyStack: mutable.Stack[(PolicyNode, Iterator[Map[Set[Observation], PolicyNode]])]
    ): mutable.Stack[(PolicyNode, Iterator[Map[Set[Observation], PolicyNode]])] = {

        val finishedNodes: mutable.Stack[(PolicyNode, Iterator[Map[Set[Observation], PolicyNode]])] = new mutable.Stack()

        while (!policyStack.isEmpty && policyStack.top._2.isEmpty) {
            finishedNodes.push(policyStack.pop())
        }
        return finishedNodes
    }

    private def getNextNode(
        top: (PolicyNode, Iterator[Map[Set[Observation], PolicyNode]])
    ): (PolicyNode, Iterator[Map[Set[Observation], PolicyNode]]) = {

        val (node, childIterator) = top
        if (childIterator.isEmpty) {
            return (node, allObservationPolicies.toIterator)
        }
        return (node, childIterator)
    }

    private def generateNodePolicyStack(
        targetPolicy: PolicyNode
    ): mutable.Stack[(PolicyNode, Iterator[Map[Set[Observation], PolicyNode]])] = {

        val policyStack: mutable.Stack[(PolicyNode, Iterator[Map[Set[Observation], PolicyNode]])] = new mutable.Stack()
        val traversedNodes: mutable.Stack[PolicyNode] = new mutable.Stack()
        traversedNodes.push(targetPolicy)

        while (!traversedNodes.isEmpty) {
            val currNode = traversedNodes.pop()
            for ((obs, node) <- currNode.transitions) {
                traversedNodes.push(node)
            }
            if (currNode.transitions.size == 0) {
                val obsPolicies = allObservationPolicies.toIterator
                currNode.setTransitions(obsPolicies.next())
                policyStack.push((currNode, obsPolicies))
            }
        }
        return policyStack
    }

    private def actionObservationPermutations(
        observations: Set[Set[Observation]],
        policies: Set[PolicyNode],
        policyTransitions: Map[Set[Observation], PolicyNode] = Map()
    ): Set[Map[Set[Observation], PolicyNode]] = {

        if (observations.isEmpty) {
            return Set(policyTransitions)
        }
        else {
            return policies.map(policy =>
                actionObservationPermutations(
                    observations.tail,
                    policies,
                    Map(observations.head -> policy) ++ policyTransitions)
            ).toSet.flatten
        }
    }
}