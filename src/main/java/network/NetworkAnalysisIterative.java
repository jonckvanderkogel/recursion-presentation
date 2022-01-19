package network;

import io.vavr.collection.Array;

public class NetworkAnalysisIterative {
    public static Long calculateSizeOfNetwork(Node root) {
        Long accumulator = 0L;
        Array<Node> tempNodes = Array.of(root);
        while (true) {
            if (tempNodes.isEmpty()) {
                return accumulator;
            }
            Node head = tempNodes.head();
            tempNodes = tempNodes.tail().appendAll(head.getChildren());
            accumulator += head.getSize();
        }
    }
}
