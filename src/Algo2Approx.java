import java.util.*;

public class Algo2Approx {

    /**
     * Executes the 2-Approx algorithm to found a 2-ratio minimum vertex cover of the given graph
     *
     * @param graph the graph
     * @return the 2-ration minimum vertex cover
     */
    public static Set<Integer> execute(Graph graph) {
        Graph copyGraph = graph.getModifiableVersion();

        Set<Integer> V = new HashSet<>();

        //Choose a new edge to covered
        int[] edge = copyGraph.takeEdge();

        //Covering all edges of the graph
        while (edge != null) {

            //Add in the vertex cover the two vertices linked to the chosen edge
            V.add(edge[0]);
            copyGraph.removeVertex(edge[0]);
            V.add(edge[1]);
            copyGraph.removeVertex(edge[1]);

            //Choose a new edge to covered
            edge = copyGraph.takeEdge();
        }

        return V;
    }

}