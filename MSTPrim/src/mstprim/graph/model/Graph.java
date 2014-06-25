package mstprim.graph.model;

import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Represents graph.
 *
 * @author Max
 */
public class Graph {
    private List<Branch> branches;
    private List<Node> nodes;

    /**
     * @param branches List of branches
     * @param nodes List of nodes
     */
    public Graph(@NotNull final List<Branch> branches, @NotNull final List<Node> nodes) {
        this.branches = branches;
        this.nodes = nodes;
    }

    /**
     * Returns a list of branches of the graph.
     *
     * @return List of branches.
     */
    @NotNull
    public List<Branch> getBranches() {
        return branches;
    }

    /**
     * Sets the list of branches of the graph.
     *
     * @param branches
     */
    public void setBranches(@NotNull final List<Branch> branches) {
        this.branches = branches;
    }

    /**
     * Returns a list of nodes of the graph.
     *
     * @return List of nodes.
     */
    @NotNull
    public List<Node> getNodes() {
        return nodes;
    }

    /**
     * Sets graph's nodes list.
     *
     * @param nodes Nodes list.
     */
    public void setNodes(@NotNull final List<Node> nodes) {
        this.nodes = nodes;
    }

    /**
     * Frees nodes and branches.
     */
    public void clear() {
        branches.forEach(branch -> branch.setTaken(false));
        nodes.forEach(node -> node.setTaken(false));
    }
}
