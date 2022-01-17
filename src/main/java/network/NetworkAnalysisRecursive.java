package network;

import io.vavr.collection.Array;
import lombok.extern.slf4j.Slf4j;
import network.setup.SetupNetwork;

import static tailrecursion.StackTraceHelper.printStackTrace;

@Slf4j
public class NetworkAnalysisRecursive {
    public static void main(String... args) {
        Node network = new SetupNetwork().setupNetwork(10);
        log.info(String.format("Size of network: %d", calculateSizeOfNetwork(network)));
    }

    public static Long calculateSizeOfNetwork(Node root) {
        return calculateSize(Array.of(root), 0L);
    }

    public static Long calculateSize(Array<Node> nodes, Long accumulator) {
        if (nodes.isEmpty()) {
            return accumulator;
        } else {
            printStackTrace();
            Node head = nodes.head();
            return calculateSize(
                nodes
                    .tail()
                    .appendAll(head.getChildren()),
                accumulator + head.getSize()
            );
        }
    }
}
