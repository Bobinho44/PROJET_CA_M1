import java.util.HashSet;
import java.util.Set;

public class Graph {

    /**
     * Fields
     */
    private final boolean[][] representation;
    private final int n;
    private double p;
    private int m;
    private int maxDegree;
    private double averageDegree;

    /**
     * Creates a new graph
     *
     * @param n the size
     * @param p the probability to have an edge between to vertices
     */
    public Graph(int n, double p) {
        this.representation = new boolean[n][n];
        this.n = n;
        this.p = p;

        this.build();
    }

    /**
     * Creates a new graph
     *
     * @param n the size
     * @param representation all edges
     */
    private Graph(int n, boolean[][] representation) {
        this.representation = representation;
        this.n = n;
    }

    /**
     * Builds a random graph with initial parameters
     */
    private void build() {
        int[] tempMaxDegree = new int[n];

        //Loop all vertices combinations
        for (int u = 0; u < n; u++) {
            for (int v = u; v < n; v++) {

                //Choose if an edge must be created between (u, v)
                if ((u != v && Math.random() < p)) {
                    tempMaxDegree[u]++;
                    tempMaxDegree[v]++;

                    //Add the (u, v) edge
                    representation[u][v] = true;
                    representation[v][u] = true;
                    m++;
                }
            }
        }

        //Calculate the max degree of the graph
        for (int i = 0; i < n; i++) {
            maxDegree = Math.max(tempMaxDegree[i], maxDegree);
        }

        //Calculate the average degree of the graph
        averageDegree = Math.round((2.* m / n) * 100) / 100.0;
    }

    /**
     * Gets the number of edge
     *
     * @return the number of edge
     */
    public int getM() {
        return m;
    }

    /**
     * Gets the max degree
     *
     * @return the max degree
     */
    public int getMaxDegree() {
        return maxDegree;
    }

    /**
     * Gets the average degree
     *
     * @return the average degree
     */
    public double getAverageDegree() {
        return averageDegree;
    }

    /**
     * Removes a vertex from the graph (the vertex is not really deleted, it is just isolated)
     *
     * @param u the vertex
     */
    public void removeVertex(int u) {

        //Loop all potentially edge from u
        for (int v = 0; v < n; v++) {

            //Deletes all edge from u
            if (representation[u][v]) {
                representation[u][v] = false;
                representation[v][u] = false;
            }

        }
    }

    /**
     * Gets the sub-graph of the actual graph minus a vertex
     *
     * @param u the vertex
     * @return the sub-graph of the actual graph minus a vertex
     */
    public Graph getSubGraph(int u) {
        Graph sub = getModifiableVersion();

        //Remove the vertex u from the sub-graph
        sub.removeVertex(u);

        return sub;
    }

    /**
     * Takes an edge from the graph
     *
     * @return an edge from the graph
     */
    public int[] takeEdge() {

        //Loop all potentially edge of the graph
        for (int u = 0; u < n; u++) {
            for (int v = u; v < n; v++) {

                //Choose the first fonded edge
                if (representation[u][v]) {
                    return new int[]{u, v};
                }

            }
        }

        //The graph contains only isolated vertices
        return null;
    }

    /**
     * Gets a vertex of degree 1
     *
     * @return a vertex of degree 1
     */
    public int takeVertexNeighbourOfDegree1() {

        //Loop all potentially edges
        for (int u = 0; u < n; u++) {
            int degree = 0;
            int neighbour = -1;
            for (int v = 0; v < n; v++) {

                //Add a degree to the vertex u and save his neighbour
                if (representation[u][v]) {
                    degree++;
                    neighbour = v;
                }

            }

            //A vertex with, edge has been founded
            if (degree == 1) {
                return neighbour;
            }

        }

        //No vertex with, edge has been founded
        return -1;
    }

    /**
     * Gets a vertex of degree greater than k
     *
     * @param k the minimum degree
     * @return a vertex of degree greater than k
     */
    public int takeVertexOfDegreeGreaterThan(int k) {

        //Loop all potentially edges
        for (int u = 0; u < n; u++) {
            int degree = 0;
            for (int v = 0; v < n; v++) {

                //Add a degree to the vertex u
                if (representation[u][v]) {
                    degree++;
                }

            }

            //A vertex with more than k edge has been founded
            if (degree > k) {
                return u;
            }

        }

        //No vertex with more than k edge has been founded
        return -1;
    }

    /**
     * Gets all non isolated vertices
     *
     * @return all non isolated vertices
     */
    public Integer[] getNonIsolatedVertices() {
        Set<Integer> nonIsolatedVertex = new HashSet<>();

        int m = 0;

        //Loop all potentially edge
        for (int u = 0; u < n; u++) {

            if (nonIsolatedVertex.contains(u)) {
                continue;
            }

            for (int v = 0; v < n; v++) {
                //Pick all founded non isolated vertices
                if (representation[u][v]) {
                    m++;
                    nonIsolatedVertex.add(u);
                    break;
                }

            }
        }

        //Update the number of edge
        this.m = m;

        //Gets all non isolated vertices
        return nonIsolatedVertex.toArray(new Integer[0]);
    }

    /**
     * Checks if the set of vertex is a vertex cover
     *
     * @param V the set of vertex
     * @return true the set of vertex is a vertex cover, false otherwise
     */
    public boolean isVertexCover(Set<Integer> V) {

        //Loop all potentially edges
        for (int u = 0; u < n; u++) {
            for (int v = u; v < n; v++) {

                //No vertex of the set covers this edge
                if (representation[u][v] && !V.contains(u) && !V.contains(v)) {
                    return false;
                }

            }
        }

        //The set of vertex is a vertex cover
        return true;
    }

    /**
     * Gets the modifiable version of the graph
     *
     * @return the modifiable version of the graph
     */
    public Graph getModifiableVersion() {

        boolean[][] copy = new boolean[n][n];

        //Copy all edges
        for (int u = 0; u < n; u++) {
            System.arraycopy(representation[u], 0, copy[u], 0, n);
        }

        //Gets the modifiable version
        return new Graph(n, copy);
    }

}