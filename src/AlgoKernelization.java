import java.util.*;

public class AlgoKernelization {

    /**
     * Executes the Kernelization algorithm to found a minimum vertex cover of the given graph
     *
     * @param graph the graph
     * @return the minimum vertex cover
     */
    public static Set<Integer> execute(Graph graph) {
        return reduce(graph, Algo2Approx.execute(graph).size());
    }

    /**
     * Performs a dichotomous search to find the optimal vertex cover size of the given graph
     *
     * @param graph the graph
     * @param lower the lower bound of the interval containing the optimal value of the minimum vertex cover size
     * @param upper the upper bound of the interval containing the optimal value of the minimum vertex cover size
     * @return the minimum vertex cover
     */
    private static Set<Integer> dichotomousResearch(Graph graph, Integer[] kernel, Set<Integer> V, int lower, int upper) {

        //End recursion condition
        if (lower  == upper) {
            return findVertexCover(graph, kernel, V, lower);
        }

        int middle = (lower + upper) / 2;

        //If the vertex cover isn't null, the minimum vertex cover size is lower or equal than the actual vertex cover size
        if (findVertexCover(graph, kernel, V, middle) != null) {

            //Search a smaller vertex cover
            return dichotomousResearch(graph, kernel, V, lower, middle);
        }

        //Search a bigger vertex cover
        return dichotomousResearch(graph, kernel, V, middle + 1, upper);
    }

    /**
     * Reduce the graph to obtain the kernel
     *
     * @param graph the graph
     * @param k the size of the vertex cover
     * @return the minimum vertex cover
     */
    private static Set<Integer> reduce(Graph graph, int k) {
        Graph copyGraph = graph.getModifiableVersion();

        Set<Integer> V = new HashSet<>();

        int isKernel = 0;

        //Use reduction rules whenever possible
        while (isKernel >= 0) {

            int vertexNeighbourOfDegree1 = copyGraph.takeVertexNeighbourOfDegree1();

            //Apply the first reduction rule can be used (vertex with degree 1)
            if (vertexNeighbourOfDegree1 != -1) {
                V.add(vertexNeighbourOfDegree1);
                copyGraph.removeVertex(vertexNeighbourOfDegree1);
                k--;
            }

            int vertexOfDegreeGreaterThanK = copyGraph.takeVertexOfDegreeGreaterThan(k);

            //Apply the second reduction rule can be used (vertex with degree > k)
            if (vertexOfDegreeGreaterThanK != -1) {
                V.add(vertexOfDegreeGreaterThanK);
                copyGraph.removeVertex(vertexOfDegreeGreaterThanK);
                k--;
            }

            //Checks if a reduction has been realized
            isKernel = Math.max(vertexNeighbourOfDegree1, vertexOfDegreeGreaterThanK);
        }

        //Gets the kernel of the graph
        Integer[] kernel = copyGraph.getNonIsolatedVertices();

        //The kernel is empty
        if (kernel.length == 0) {
            return V;
        }

        //Find a minimum vertex cover of the founder kernel
        return dichotomousResearch(copyGraph, kernel, V, k / 2, k);
    }

    /**
     * Finds the minimum vertex cover of the graph
     *
     * @param graph the graph
     * @param V the actual vertex cover
     * @param k the size of the kernel vertex cover
     * @return the minimum vertex cover of the graph
     */
    private static Set<Integer> findVertexCover(Graph graph, Integer[] kernel, Set<Integer> V, int k) {

        Set<Integer> copyV = new HashSet<>(V);

        //Loop all potentially vertex cover of the kernel
        for (Set<Integer> v : generateCombinations(new Integer[k], kernel, 0, kernel.length-1, 0)) {

            //Found a vertex cover of the kernel
            if (graph.isVertexCover(v)) {
                copyV.addAll(v);
                return copyV;
            }
        }

        //No vertex cover was found
        return null;
    }

    /**
     * Generates all combinations of vertex cover
     *
     * @param current      the combination in construction
     * @param V            the usable vertex
     * @param start        the index of the first vertex of the new combination
     * @param end          the index of the last vertex of the new combination
     * @param index        the construction index of the combination
     * @return all combinations of vertex cover
     */
    private static List<Set<Integer>> generateCombinations(Integer[] current, Integer[] V, int start, int end, int index) {

        //End recursion condition (the combination is created)
        if (index == current.length) {
            return List.of(new HashSet<>(List.of(current.clone())));
        }

        List<Set<Integer>> combinations = new ArrayList<>();

        //Build the new combination
        if (start <= end) {
            current[index] = V[start];
            combinations.addAll(generateCombinations(current,  V, start + 1, end, index + 1));
            combinations.addAll(generateCombinations(current, V, start + 1, end, index));
        }

        return combinations;
    }

}