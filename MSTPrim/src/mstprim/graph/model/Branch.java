package mstprim.graph.model;

import com.sun.istack.internal.NotNull;

/**
 * Represents a branch of graph.
 *
 * @author Max
 */
public class Branch {
    protected Node from_node;
    protected Node to_node;
    protected long length;
    protected boolean taken; // Is the branch counted by MST algorithm

    /**
     * @param length Branch's length
     */
    public Branch(final long length) {
        this.length = length;
    }

    /**
     * @param length Branch's length
     * @param from_node Source node
     * @param to_node Destination node
     */
    public Branch(final long length, @NotNull final Node from_node, @NotNull final Node to_node) {
        this(length);
        this.from_node = from_node;
        this.to_node = to_node;
    }

    /**
     * Returns source node.
     *
     * @return Source node.
     */
    @NotNull
    public Node getFrom() {
        return from_node;
    }

    /**
     * Returns destination node.
     *
     * @return Destination node.
     */
    @NotNull
    public Node getTo() {
        return to_node;
    }

    /**
     * @return Whether the branch taken?
     */
    public boolean isTaken() {
        return taken;
    }

    /**
     * Sets taken flag to given value
     * @param taken Value given to set taken state.
     */
    public void setTaken(final boolean taken) {
        this.taken = taken;
    }

    public long getLength() {
        return length;
    }
}
