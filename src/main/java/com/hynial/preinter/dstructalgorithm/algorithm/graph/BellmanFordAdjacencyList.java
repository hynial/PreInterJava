package com.hynial.preinter.dstructalgorithm.algorithm.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An implementation of the Bellman-Ford algorithm. The algorithm finds the shortest path between a
 * starting node and all other nodes in the graph. The algorithm also detects negative cycles.
 * @https://github.com/williamfiset/Algorithms/tree/master/src/main/java/com/williamfiset/algorithms/graphtheory
 * @https://github.com/williamfiset/Algorithms/blob/master/src/main/java/com/williamfiset/algorithms/graphtheory/BellmanFordAdjacencyList.java
 *
 * @https://juejin.cn/post/6844903661395509262
 */
public class BellmanFordAdjacencyList {
    private static class Edge{
        double cost;
        int from, to;

        public Edge(int from, int to, double cost){
            this.from = from;
            this.to = to;
            this.cost = cost;
        }
    }

    public static List<Edge>[] createGraph(int V){
        List<Edge>[] graph = new List[V];
        for(int i = 0; i < V; i++) graph[i] = new ArrayList<>();
        return graph;
    }

    public static void addEdge(List<Edge>[] graph, int from, int to, double cost){
        graph[from].add(new Edge(from, to, cost));
    }

    public static double[] bellmanFord(List<Edge>[] graph, int V, int start) {
        double[] dist = new double[V];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        dist[start] = 0;

        // 对每个顶点松弛所有的边
        for(int i = 0; i < V - 1; i++){
            for(List<Edge> edges : graph){
                for(Edge edge : edges){
                    if(dist[edge.from] + edge.cost < dist[edge.to])
                        dist[edge.to] = dist[edge.from] + edge.cost;
                }
            }
        }

        // 找出负环圈 - 在完成这么多次松弛后如果还是可以松弛的话，那么就意味着，其中包含负环。
        for (int i = 0; i < V - 1; i++)
            for (List<Edge> edges : graph)
                for (Edge edge : edges)
                    if (dist[edge.from] + edge.cost < dist[edge.to]) dist[edge.to] = Double.NEGATIVE_INFINITY;

        // Return the array containing the shortest distance to every node
        return dist;
    }

    public static void main(String[] args) {

        int E = 10, V = 9, start = 0;
        List<Edge>[] graph = createGraph(V);
        addEdge(graph, 0, 1, 1);
        addEdge(graph, 1, 2, 1);
        addEdge(graph, 2, 4, 1);
        addEdge(graph, 4, 3, -3);
        addEdge(graph, 3, 2, 1);
        addEdge(graph, 1, 5, 4);
        addEdge(graph, 1, 6, 4);
        addEdge(graph, 5, 6, 5);
        addEdge(graph, 6, 7, 4);
        addEdge(graph, 5, 7, 3);
        double[] d = bellmanFord(graph, V, start);

        for (int i = 0; i < V; i++)
            System.out.printf("The cost to get from node %d to %d is %.2f\n", start, i, d[i]);

        // Output:
        // The cost to get from node 0 to 0 is 0.00
        // The cost to get from node 0 to 1 is 1.00
        // The cost to get from node 0 to 2 is -Infinity
        // The cost to get from node 0 to 3 is -Infinity
        // The cost to get from node 0 to 4 is -Infinity
        // The cost to get from node 0 to 5 is 5.00
        // The cost to get from node 0 to 6 is 5.00
        // The cost to get from node 0 to 7 is 8.00
        // The cost to get from node 0 to 8 is Infinity
    }
}
