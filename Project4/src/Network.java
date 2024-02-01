import java.util.ArrayList;
import java.util.List;



public abstract class Network {

	// To avoid overflow, set infinity to a value less than Long.MAX_VALUE;
    static final int INF = Integer.MAX_VALUE / 2;

    // Inputs: n = number of nodes, s = source, t = sink
    protected final int nVertices, sourceID, sinkID;

    // Indicates whether the network flow algorithm has ran. The solver only
    // needs to run once because it always yields the same result.
    private boolean solved;

    // The maximum flow. Calculated by calling the {@link #solve} method.
    protected int maxFlow;

    // The adjacency list representing the flow graph.
    protected List<Edge>[] graph;

    /**
     * Creates an instance of a flow network solver. Use the {@link #addEdge} method to add edges to
     * the graph.
     *
     * @param n - The number of nodes in the graph including s and t.
     * @param s - The index of the source node, 0 <= s < n
     * @param t - The index of the sink node, 0 <= t < n and t != s
     */
    public Network(int nVertices, int sourceID, int sinkID) {
      this.nVertices = nVertices;
      this.sourceID = sourceID;
      this.sinkID = sinkID;
      initializeEmptyFlowGraph();
    }

    // Constructs an empty graph with n nodes including s and t.
    @SuppressWarnings("unchecked")
    private void initializeEmptyFlowGraph() {
      graph = new List[nVertices];
      for (int i = 0; i < nVertices; i++) graph[i] = new ArrayList<Edge>();
    }

    /**
     * Adds a directed edge (and its residual edge) to the flow graph.
     *
     * @param from - The index of the node the directed edge starts at.
     * @param to - The index of the node the directed edge ends at.
     * @param capacity - The capacity of the edge
     */
    public void addEdge(Vertex from, Vertex to, int capacity) {
      if (capacity >= 0) {   // >= yaptým
//    	  throw new IllegalArgumentException("Forward edge capacity <= 0");
      Edge e1 = new Edge(from, to, capacity);
      Edge e2 = new Edge(to, from, 0);
      e1.setResidual(e2);
      e2.setResidual(e1);
      graph[from.getID()].add(e1);
      graph[to.getID()].add(e2);
      }
    }

    /**
     * Returns the residual graph after the solver has been executed. This allows you to inspect the
     * {@link Edge#flow} and {@link Edge#capacity} values of each edge. This is useful if you are
     * debugging or want to figure out which edges were used during the max flow.
     */
    public List<Edge>[] getGraph() {
      execute();
      return graph;
    }

    // Returns the maximum flow from the source to the sink.
    public int getMaxFlow() {
      execute();
      return maxFlow;
    }

    // Wrapper method that ensures we only call solve() once
    private void execute() {
      if (solved) return;
      solved = true;
      solve();
    }

    // Method to implement which solves the network flow problem.
    public abstract void solve();

	public boolean isSolved() {
		return solved;
	}

    

}
	

