import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter the graph size :");

        try {
            int n = Integer.parseInt(myObj.nextLine());

            System.out.println("Enter the edge probability :");

            try {
                double p = Double.parseDouble(myObj.nextLine());

                if (p > 1.0D || p < 0.0D) {
                    throw new NumberFormatException();
                }

                Graph graph = new Graph(n, p);

                Set<Integer> MVC2Approx = Algo2Approx.execute(graph);
                Set<Integer> MVCBoundedSearchTree = AlgoBoundedSearchTree.execute(graph);
                Set<Integer> MVCKernelization = AlgoKernelization.execute(graph);

                System.out.println(" ");
                System.out.println("m : " + graph.getM());
                System.out.println("average degree : " + graph.getAverageDegree());
                System.out.println("max degree : " + graph.getMaxDegree());
                System.out.println(" ");
                System.out.println("-------- 2-Approx -----------------");
                System.out.println(" ");
                System.out.println("size : " + MVC2Approx.size());
                System.out.println("MVC : " + MVC2Approx);
                System.out.println(" ");
                System.out.println("-----------------------------------");
                System.out.println(" ");
                System.out.println("-------- BoundedSearchTree --------");
                System.out.println(" ");
                System.out.println("size : " + MVCBoundedSearchTree.size());
                System.out.println("MVC : " + MVCBoundedSearchTree);
                System.out.println(" ");
                System.out.println("-----------------------------------");
                System.out.println(" ");
                System.out.println("-------- Kernelization ------------");
                System.out.println(" ");
                System.out.println("size : " + MVCKernelization.size());
                System.out.println("MVC : " + MVCKernelization);
                System.out.println(" ");
                System.out.println("-----------------------------------");
            }

            catch (NumberFormatException e) {
                System.out.println("This is not a valid probability!");
            }
        }

        catch (NumberFormatException e) {
            System.out.println("This is not a valid size!");
        }
    }

}