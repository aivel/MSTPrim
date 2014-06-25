package mstprim;

import mstprim.graph.model.Branch;
import mstprim.graph.model.Graph;
import mstprim.graph.model.Node;

import java.util.*;

/**
 * Generates random graphs.
 *
 * @author Max
 */
public class RandomGraphFactory {
    static private final Random random = new Random();
    static private Graph graph = null;
    /**
     * {@value #MIN_NODES_AMOUNT} minimum nodes amount to be generated.
     */
    static private final int MIN_NODES_AMOUNT = 3;
    /**
     * {@value #MIN_BRANCHES_AMOUNT} minimum branches amount to be generated.
     */
    static private final int MIN_BRANCHES_AMOUNT = MIN_NODES_AMOUNT - 1;
    /**
     * {@value #MIN_BRANCH_LENGTH} for generated branch, its minimum length.
     */
    static private final int MIN_BRANCH_LENGTH = 5;
    static private int NODES_AMOUNT;
    static private int BRANCHES_AMOUNT;
    /**
     * {@value #DEFAULT_MAX_NODES_AMOUNT} maximum nodes amount to be generated; default value.
     */
    static private final int DEFAULT_MAX_NODES_AMOUNT = 13;
    /**
     * {@value #DEFAULT_MAX_BRANCHES_AMOUNT} maximum branches amount to be generated; default value.
     */
    static private final int DEFAULT_MAX_BRANCHES_AMOUNT = DEFAULT_MAX_NODES_AMOUNT * 2;
    /**
     * {@value #DEFAULT_MAX_BRANCH_LENGTH} for generated branch, its maximum length; default value.
     */
    static private final int DEFAULT_MAX_BRANCH_LENGTH = 32;
    static private final float CHANCE = 0.3f;

    /**
     *
     * @return New randomly generated graph.
     * @throws Exception If graph == null
     */
    static public Graph getGraph() throws Exception {
        if (graph == null)
            graph = generateRandomGraph(DEFAULT_MAX_NODES_AMOUNT, DEFAULT_MAX_BRANCHES_AMOUNT, DEFAULT_MAX_BRANCH_LENGTH);
        return graph;
    }

    /**
     *
     * @param MAX_NODES_AMOUNT Preferred maximum nodes amount for graph to be randomly generated.
     * @param MAX_BRANCHES_AMOUNT Preferred maximum branches amount for graph to be randomly generated.
     * @param MAX_BRANCH_LENGTH Preferred maximum branch length for graph to be randomly generated.
     * @return
     * @throws Exception
     */
    static public Graph generateRandomGraph(final int MAX_NODES_AMOUNT,
                                            final int MAX_BRANCHES_AMOUNT,
                                            final int MAX_BRANCH_LENGTH) throws Exception {
        if (MAX_NODES_AMOUNT < MIN_NODES_AMOUNT)
            throw new Exception("MAX_NODES_AMOUNT can not be less than MIN_NODES_AMOUNT(" + MIN_NODES_AMOUNT + ")");

        if (MAX_BRANCHES_AMOUNT < MIN_BRANCHES_AMOUNT)
            throw new Exception("MAX_BRANCHES_AMOUNT can not be less than MIN_BRANCHES_AMOUNT(" + MIN_BRANCHES_AMOUNT + ")");

        if (MAX_BRANCH_LENGTH < MIN_BRANCH_LENGTH)
            throw new Exception("MAX_BRANCH_LENGTH can not be less than MIN_BRANCH_LENGTH(" + MIN_BRANCH_LENGTH + ")");

        do
            NODES_AMOUNT = Math.abs(random.nextInt() % MAX_NODES_AMOUNT) + 1;
        while (NODES_AMOUNT < MIN_NODES_AMOUNT);

        do
            BRANCHES_AMOUNT = Math.abs(random.nextInt() % MAX_BRANCHES_AMOUNT) + 1;
        while (BRANCHES_AMOUNT < NODES_AMOUNT);

        final List<Node> nodes = new ArrayList<>(NODES_AMOUNT);
        final List<Branch> branches = new ArrayList<>();

        for (int i = 0; i < NODES_AMOUNT; i++)
            nodes.add(new Node(i + 1));



        for (int from = 0; from < NODES_AMOUNT - 1; from++) {
            for (int to = from + 1; to < NODES_AMOUNT; to++) {
                int branch_length = 0;

                do
                    branch_length = Math.abs(random.nextInt() % MAX_BRANCH_LENGTH + 1);
                while (branch_length < MIN_BRANCH_LENGTH);

                final Node from_node = nodes.get(from);
                final Node to_node = nodes.get(to);
                final Branch branch_from_to = new Branch(branch_length, from_node, to_node);

                branches.add(branch_from_to);
                from_node.addBranch(branch_from_to);
            }
        }

        final int iters = (int)((1.0f - CHANCE) * 10);

        for (int i = 0; i < iters; i++) {
            List<Branch> to_remove = new ArrayList<>();

            branches.forEach(branch -> {
                if (random.nextFloat() <= CHANCE) {
                    if (branch.getFrom().getBranchesCount() > 1 &&
                        branch.getTo().getBranchesCount() > 1) {
                        to_remove.add(branch);
                    }
                }
            });

            to_remove.forEach(branch -> {
                branches.remove(branch);

                nodes.forEach(node -> node.getBranches().remove(branch));
            });
        }

        graph = new Graph(branches, nodes);
        return graph;
    }
}
