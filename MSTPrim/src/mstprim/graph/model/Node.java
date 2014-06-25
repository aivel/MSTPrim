package mstprim.graph.model;

import com.sun.istack.internal.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Represents a node of graph.
 *
 * @author Max
 */
public class Node implements Comparable<Node> {
    protected long id;
    protected List<Branch> branches;
    private boolean taken;
    //
    private static Random random = new Random();
    private double x = 0;
    private double y = 0;
    private boolean dragged = false;

    /**
     * @param id Node's ID.
     */
    public Node(final long id) {
        this.setId(id);
        branches = new LinkedList<>();
        //
        x = (int) (200 + (random.nextFloat() * 300));
        y = (int) (100 + (random.nextFloat() * 200));
    }

    /**
     * Adds branch to this node.
     *
     * @param branch A branch.
     * @return True if branch successfully added; in other case returns false.
     */
    public boolean addBranch(@NotNull final Branch branch) {
        if (branch.getFrom().getId() != getId()
                && branch.getTo().getId() != getId()
                || !getBranches().add(branch))
            return false;

        return true;
    }

    /**
     * Returns amount of branches linked to the node.
     */
    public int getBranchesCount() {
        return getBranches().size();
    }

    /**
     * Returns Node's ID.
     *
     * @return node's id.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     * @param id New node's id.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns branches, linked to the node.
     *
     * @return List of branches.
     */
    @NotNull
    public List<Branch> getBranches() {
        return branches;
    }

    /**
     * @return Whether the node dragged?
     */
    public boolean isDragged() {
        return dragged;
    }

    /**
     * Sets node's dragging status.
     *
     * @param dragged Dragging status to be set.
     */
    public void setDragged(boolean dragged) {
        this.dragged = dragged;
    }

    /**
     * Returns X position.
     *
     * @return X position.
     */
    public double getX() {
        return x;
    }

    /**
     * Sets X position.
     *
     * @param x X position.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Returns Y position.
     *
     * @return Y position.
     */
    public double getY() {
        return y;
    }

    /**
     * Sets Y position.
     *
     * @param y position.
     */
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public int compareTo(Node o) {
        return getBranchesCount() - o.getBranchesCount();
    }

    /**
     * @return Whether the branch taken?
     */
    public boolean isTaken() {
        return taken;
    }

    /**
     * Sets taken flag to given value
     *
     * @param taken Value given to set taken state.
     */
    public void setTaken(boolean taken) {
        this.taken = taken;
    }
}
