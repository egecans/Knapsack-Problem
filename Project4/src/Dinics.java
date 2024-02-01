import static java.lang.Math.min;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;



public class Dinics extends Network {

    private int[] level;

    /**
     * Creates an instance of a flow network solver. Use the {@link #addEdge} method to add edges to
     * the graph.
     *
     * @param n - The number of nodes in the graph including source and sink nodes.
     * @param s - The index of the source node, 0 <= s < n
     * @param t - The index of the sink node, 0 <= t < n, t != s
     */
    public Dinics(int nVertices, int sourceID, int sinkID) {
      super(nVertices, sourceID, sinkID);
      level = new int[nVertices];
    }

    @Override
    public void solve() {
      // next[i] indicates the next edge index to take in the adjacency list for node i. This is
      // part
      // of the Shimon Even and Alon Itai optimization of pruning deads ends as part of the DFS
      // phase.
      int[] next = new int[nVertices];

      while (bfs()) {
        Arrays.fill(next, 0);
        // Find max flow by adding all augmenting path flows.
        for (int f = dfs(sourceID, next, INF); f != 0; f = dfs(sourceID, next, INF)) {
          maxFlow += f;
        }
      }
    }
    
    // Do a BFS from source to sink and compute the depth/level of each node
    // which is the minimum number of edges from that node to the source.
    private boolean bfs() {
      Arrays.fill(level, -1);
      Deque<Integer> q = new ArrayDeque<>(nVertices);
      q.offer(sourceID);
      level[sourceID] = 0;
      while (!q.isEmpty()) {
        int node = q.poll();
        for (Edge edge : graph[node]) {
          int cap = edge.remainingCapacity();
          // selected edge capacity must be greater than 0, and that will be unvisited. 
          if (cap > 0 && level[edge.getTo().getID()] == -1) {
            level[edge.getTo().getID()] = level[node] + 1;
            q.offer(edge.getTo().getID());
          }
        }
      }
      // When we couldn't reach the sink, that will stay -1 and we learn that there is no augment path.
      return level[sinkID] != -1;
    }

    private int dfs(int currVertex, int[] next, int flow) {
      if (currVertex == sinkID) return flow;
      final int numEdges = graph[currVertex].size();

      for (; next[currVertex] < numEdges; next[currVertex]++) {
        Edge edge = graph[currVertex].get(next[currVertex]);
        int cap = edge.remainingCapacity();
        // remaining capacity must be greater than 0, and the visited vertex level must be 1 greater than the parent.
        if (cap > 0 && level[edge.getTo().getID()] == level[currVertex] + 1) {
        	
          //this will continue recursively untill find the min flow.
          int bottleNeck = dfs(edge.getTo().getID(), next, min(flow, cap));
          if (bottleNeck > 0) {
            edge.augment(bottleNeck);
            return bottleNeck;
          }
        }
      }
      return 0;
    }
  }
