package network

import io.vavr.collection.Array
import network.setup.SetupNetwork
import org.slf4j.LoggerFactory

fun main() {
    val network = SetupNetwork().setupNetwork(10000)
    val networkAnalysisTailRecursive = NetworkAnalysis()
    val size = networkAnalysisTailRecursive.calculateSizeOfNetwork(network)
    val logger = LoggerFactory.getLogger(NetworkAnalysis::class.java)
    logger.info(String.format("Size of network: %d", size))
}

class NetworkAnalysis {

    fun calculateSizeOfNetwork(root: Node): Long? {
        return calculateSizeTailRecursion(Array.of(root), 0L)
    }

    tailrec fun calculateSizeTailRecursion(nodes: Array<Node>, accumulator: Long): Long? {
        return if (nodes.isEmpty) {
            accumulator
        } else {
            val head = nodes.head()
            calculateSizeTailRecursion(
                nodes
                    .tail()
                    .appendAll(head.children),
                accumulator + head.size
            )
        }
    }

    fun calculateSizeHeadRecursion(nodes: Array<Node>, accumulator: Long): Long? {
        return if (nodes.isEmpty) {
            accumulator
        } else {
            val head = nodes.head()
            calculateSizeHeadRecursion(
                nodes
                    .tail()
                    .appendAll(head.children),
                accumulator + head.size
            )
        }
    }
}