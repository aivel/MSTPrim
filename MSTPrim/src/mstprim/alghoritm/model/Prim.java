package mstprim.alghoritm.model;

import com.sun.istack.internal.NotNull;
import mstprim.graph.model.Graph;
import mstprim.graph.model.Node;
import mstprim.graph.model.Branch;

import java.util.*;

/**
 * Performs Prim's algorithm on given graph.
 *
 * @author Max
 */
public class Prim {
    /**
     * Performs Prim's algorithm on given graph.
     * @param graph Graph to perform Prim's algorithm on.
     * @return Taken branches.
     */
    @NotNull
    public List<Branch> act(@NotNull final Graph graph) {
        final List<Node> nodes = graph.getNodes();
        final List<Branch> taken_branches = new LinkedList<>();

        final int rand_start_index = Math.abs(new Random().nextInt() % nodes.size());
        nodes.get(rand_start_index).setTaken(true);

        while (nodes.stream().anyMatch(node -> !node.isTaken())) {
            Branch shortest_branch = null;

            for (Node node: nodes) {
                for (Branch branch_of_node: node.getBranches()) {
                    if (!branch_of_node.getFrom().isTaken() && !branch_of_node.getTo().isTaken()
                            || branch_of_node.getFrom().isTaken() && branch_of_node.getTo().isTaken()
                            || branch_of_node.isTaken()
                            || branch_of_node == shortest_branch)
                        continue;

                    if (shortest_branch == null
                    || (branch_of_node.getLength() <= shortest_branch.getLength()))
                        shortest_branch = branch_of_node;
                }
            }

            if (shortest_branch == null || shortest_branch.isTaken())
                continue;

            taken_branches.add(shortest_branch);
            shortest_branch.setTaken(true);
            shortest_branch.getFrom().setTaken(true);
            shortest_branch.getTo().setTaken(true);
        }

        return taken_branches;
    }


}