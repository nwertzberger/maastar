package maastar.policy

/**
 * Created by nwertzberger on 6/25/14.
 */
object PolicyValueOrdering extends Ordering[Policy] {
    def compare(p1: Policy, p2: Policy) = (p1.value + p1.estimate) compare (p2.value + p2.estimate)
}
