import java.util.LinkedList;
import java.util.Queue;

public class MaxFlow {

    // =========================================================
    // BFS - FIND AUGMENTING PATH
    // =========================================================

    private static boolean bfs(
            int[][] residualGraph,
            int source,
            int sink,
            int[] parent) {

        int vertices = residualGraph.length;

        boolean[] visited =
                new boolean[vertices];

        Queue<Integer> queue =
                new LinkedList<>();

        // Start BFS from source
        queue.add(source);
        visited[source] = true;
        parent[source] = -1;

        while (!queue.isEmpty()) {

            int current =
                    queue.poll();

            for (int next = 0;
                 next < vertices;
                 next++) {

                // There is available capacity
                // and node is not visited
                if (!visited[next]
                        && residualGraph[current][next] > 0) {

                    queue.add(next);

                    parent[next] = current;

                    visited[next] = true;

                    // Sink reached
                    if (next == sink) {
                        return true;
                    }
                }
            }
        }

        return false;
    }


    // =========================================================
    // EDMONDS-KARP MAX FLOW
    // =========================================================

    public static int edmondsKarp(
            int[][] capacity,
            int source,
            int sink) {

        int vertices =
                capacity.length;

        // -----------------------------------------------------
        // Create residual graph
        // -----------------------------------------------------

        int[][] residualGraph =
                new int[vertices][vertices];

        for (int i = 0;
             i < vertices;
             i++) {

            for (int j = 0;
                 j < vertices;
                 j++) {

                residualGraph[i][j] =
                        capacity[i][j];
            }
        }


        // Parent array stores the augmenting path
        int[] parent =
                new int[vertices];

        int maxFlow = 0;


        // -----------------------------------------------------
        // Keep finding augmenting paths
        // -----------------------------------------------------

        while (
                bfs(
                        residualGraph,
                        source,
                        sink,
                        parent
                )
        ) {

            // -------------------------------------------------
            // Find bottleneck capacity
            // -------------------------------------------------

            int pathFlow =
                    Integer.MAX_VALUE;

            int current = sink;

            while (current != source) {

                int previous =
                        parent[current];

                pathFlow =
                        Math.min(
                                pathFlow,
                                residualGraph[previous][current]
                        );

                current = previous;
            }


            // -------------------------------------------------
            // Update residual capacities
            // -------------------------------------------------

            current = sink;

            while (current != source) {

                int previous =
                        parent[current];

                // Forward edge
                residualGraph[previous][current]
                        -= pathFlow;

                // Reverse edge
                residualGraph[current][previous]
                        += pathFlow;

                current = previous;
            }


            // Add flow
            maxFlow += pathFlow;
        }

        return maxFlow;
    }


    // =========================================================
    // MAX FLOW WITH STEP-BY-STEP DISPLAY
    // =========================================================

    public static int calculateMaxFlow(
            int[][] capacity,
            int source,
            int sink) {

        int vertices =
                capacity.length;


        // -----------------------------------------------------
        // Create residual graph
        // -----------------------------------------------------

        int[][] residualGraph =
                new int[vertices][vertices];

        for (int i = 0;
             i < vertices;
             i++) {

            for (int j = 0;
                 j < vertices;
                 j++) {

                residualGraph[i][j] =
                        capacity[i][j];
            }
        }


        int[] parent =
                new int[vertices];

        int maxFlow = 0;

        int pathNumber = 1;


        System.out.println();
        System.out.println(
                "========================================"
        );

        System.out.println(
                "       EDMONDS-KARP MAX FLOW"
        );

        System.out.println(
                "========================================"
        );


        // -----------------------------------------------------
        // Find augmenting paths
        // -----------------------------------------------------

        while (
                bfs(
                        residualGraph,
                        source,
                        sink,
                        parent
                )
        ) {

            // -------------------------------------------------
            // Find bottleneck
            // -------------------------------------------------

            int pathFlow =
                    Integer.MAX_VALUE;

            int current = sink;

            while (current != source) {

                int previous =
                        parent[current];

                pathFlow =
                        Math.min(
                                pathFlow,
                                residualGraph[previous][current]
                        );

                current = previous;
            }


            // -------------------------------------------------
            // Display path
            // -------------------------------------------------

            System.out.print(
                    "Augmenting Path "
                            + pathNumber
                            + " : "
            );

            int[] path =
                    new int[vertices];

            int pathLength = 0;

            current = sink;

            while (current != source) {

                path[pathLength++] =
                        current;

                current =
                        parent[current];
            }

            path[pathLength++] =
                    source;


            // Print path in correct order
            for (int i = pathLength - 1;
                 i >= 0;
                 i--) {

                System.out.print(
                        getNodeName(path[i])
                );

                if (i > 0) {
                    System.out.print(" -> ");
                }
            }

            System.out.println();


            System.out.println(
                    "Path Flow       : "
                            + pathFlow
            );


            // -------------------------------------------------
            // Update residual graph
            // -------------------------------------------------

            current = sink;

            while (current != source) {

                int previous =
                        parent[current];

                // Reduce forward capacity
                residualGraph[previous][current]
                        -= pathFlow;

                // Increase reverse capacity
                residualGraph[current][previous]
                        += pathFlow;

                current = previous;
            }


            // -------------------------------------------------
            // Update total flow
            // -------------------------------------------------

            maxFlow += pathFlow;


            System.out.println(
                    "Current Max Flow : "
                            + maxFlow
            );

            System.out.println(
                    "----------------------------------------"
            );

            pathNumber++;
        }


        // -----------------------------------------------------
        // Final result
        // -----------------------------------------------------

        System.out.println(
                "Maximum Flow     : "
                        + maxFlow
        );

        System.out.println(
                "========================================"
        );

        return maxFlow;
    }


    // =========================================================
    // NODE NAMES
    // =========================================================

    private static String getNodeName(
            int node) {

        switch (node) {

            case 0:
                return "SOURCE";

            case 1:
                return "VEGETABLES";

            case 2:
                return "MEAT";

            case 3:
                return "SPICES";

            case 4:
                return "RICE";

            case 5:
                return "RECIPE";

            case 6:
                return "SINK";

            default:
                return "NODE-" + node;
        }
    }
}