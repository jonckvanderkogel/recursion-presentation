package network;

import io.vavr.collection.Array;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public class Node {
    private final Long size;
    private final Array<? extends Node> children;

    public Array<? extends Node> getChildren() {
        return this.children;
    }

    public Long getSize() {
        return this.size;
    }
}
