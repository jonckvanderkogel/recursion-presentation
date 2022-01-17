package network.setup;

import io.vavr.collection.Array;
import lombok.extern.slf4j.Slf4j;
import network.Node;
import tailrecursion.TailCall;

import java.util.SplittableRandom;

import static tailrecursion.TailCalls.done;

@Slf4j
public class SetupNetwork {
    private static final SplittableRandom random = new SplittableRandom();

    public static void main(String... args) {
        SetupNetwork setupNetwork = new SetupNetwork();

        log.info(setupNetwork.setupNetwork(1000).toString());
    }

    public Node setupNetwork(int nrOfNodes) {
        return connectNetwork(
            initializeNodes(nrOfNodes)
                .sorted(),
            Array.empty()
        ).invoke();
    }

    private Array<SetupNode> initializeNodes(int nrOfNodes) {
        return initializeNodes(Array.empty(), nrOfNodes, nrOfNodes - 1).invoke();
    }

    private int determineNrOfConnections(int available, boolean lastNode) {
        return lastNode ? available : available > 0 ? random.nextInt(0, (int) Math.ceil((double) available / 2) + 1) : 0;
    }

    private TailCall<Array<SetupNode>> initializeNodes(final Array<SetupNode> nodes, final int nrOfNodes, final int availableIndex) {
        if (nrOfNodes == 0) {
            return done(nodes);
        } else {
            int nrOfConnections = determineNrOfConnections(availableIndex, nrOfNodes == 1);
            return () -> initializeNodes(
                nodes.append(
                    new SetupNode(
                        random.nextLong(1, 1000),
                        Array.empty(),
                        nrOfConnections)
                ),
                nrOfNodes - 1,
                availableIndex - nrOfConnections);
        }
    }

    private TailCall<SetupNode> connectNetwork(final Array<SetupNode> nodes, final Array<SetupNode> eligible) {
        if (nodes.size() == 0) {
            return done(eligible.head());
        } else {
            SetupNode head = nodes.head();
            SetupNode newNode = SetupNode.of(head, eligible.take(head.getNrOfConnections()));
            Array<SetupNode> newEligible = eligible.takeRight(eligible.size() - head.getNrOfConnections()).append(newNode);
            return () -> connectNetwork(nodes.tail(), newEligible);
        }
    }
}
