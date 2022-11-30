import java.util.HashSet;
import java.util.Set;

public class AlgoBoundedSearchTree {

    /**
     * Executes the BST algorithm to found a minimum vertex cover of the given graph
     *
     * @param graph the graph
     * @return the minimum vertex cover
     */
    public static Set<Integer> execute(Graph graph) {
        int t = Algo2Approx.execute(graph).size();

        return dichotomousResearch(graph, t / 2, t);
    }

    /**
     * Performs a dichotomous search to find the optimal vertex cover size of the given graph
     *
     * @param graph the graph
     * @param lower the lower bound of the interval containing the optimal value of the minimum vertex cover size
     * @param upper the upper bound of the interval containing the optimal value of the minimum vertex cover size
     * @return the minimum vertex cover
     */
    private static Set<Integer> dichotomousResearch(Graph graph, int lower, int upper) {

        //End recursion condition
        if (lower  == upper) {
            return createSearchTree(graph, lower, new HashSet<>());
        }

        int middle = (lower + upper) / 2;

        //If the vertex cover isn't null, the minimum vertex cover size is lower or equal than the actual vertex cover size
        if (createSearchTree(graph, middle, new HashSet<>()) != null) {

            //Search a smaller vertex cover
            return dichotomousResearch(graph, lower, middle);
        }

        //Search a bigger vertex cover
        return dichotomousResearch(graph, middle + 1, upper);
    }

    /**
     * Uses a search tree to find a vertex cover of the graph, of size k
     *
     * @param graph the graph
     * @param k the vertex cover size
     * @param V the actual vertex cover
     * @return the optional vertex cover
     */
    public static Set<Integer> createSearchTree(Graph graph, int k, Set<Integer> V) {

        //Choose a new edge to covered
        int[] edge = graph.takeEdge();

        //End recursion condition (all edges have been covered)
        if (edge == null) {
            return V;
        }

        //End recursion condition (not all edges have been covered with the maximum number of allowed vertex)
        if (k == 0) {
            return null;
        }

        //Add the first vertex of the chosen edge to the vertex cover
        Set<Integer> V1 = new HashSet<>(V);
        V1.add(edge[0]);

        //Search a vertex cover of the graph without the selected vertex
        Set<Integer> V1F = createSearchTree(graph.getSubGraph(edge[0]), k - 1, V1);

        //Return the founded vertex cover of size k
        if (V1F != null) {
            return V1F;
        }

        //Add the second vertex of the chosen edge to the vertex cover
        Set<Integer> V2 = new HashSet<>(V);
        V2.add(edge[1]);

        //Return the optional vertex cover of size k (if null, there is no vertex cover of size k)
        return createSearchTree(graph.getSubGraph(edge[1]), k - 1, V2);
    }

}