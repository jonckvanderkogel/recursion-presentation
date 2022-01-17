package network.setup;

import io.vavr.collection.Array;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import network.Node;

@ToString(callSuper = true)
@Getter
@Setter
public class SetupNode extends Node implements Comparable<SetupNode> {
    private final int nrOfConnections;

    public SetupNode(Long size, Array<? extends Node> children, int nrOfConnections) {
        super(size, children);
        this.nrOfConnections = nrOfConnections;
    }

    public static SetupNode of(SetupNode parent, Array<? extends Node> children) {
        return new SetupNode(parent.getSize(), children, parent.getNrOfConnections());
    }

    @Override
    public int compareTo(SetupNode o) {
        return this.nrOfConnections - o.getNrOfConnections();
    }
}
