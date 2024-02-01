

public class Edge {
	

    private Vertex from, to;
    private Edge residual;
    private int flow;
    private final int capacity;

    public Edge(Vertex from, Vertex to, int capacity) {
      this.from = from;
      this.to = to;
      this.capacity = capacity;
    }

    public boolean isResidual() {
      return capacity == 0;
    }

    public int remainingCapacity() {
      return capacity - flow;
    }

    public void augment(int bottleNeck) {
      flow += bottleNeck;
      residual.flow -= bottleNeck;
      if ( from.getType().equals("bag") )
    	  to.getRemainingCapacity(bottleNeck);
      
    }
    
    public String toString(Vertex source, Vertex sink) {
      String u = (from.getType().equals("source")) ? "s" : ((from.getType().equals("sink")) ? "t" : String.valueOf(from.getID()));
      String v = (from.getType().equals("source")) ? "s" : ((from.getType().equals("sink")) ? "t" : String.valueOf(to.getID()));
      return String.format(
          "Edge %s -> %s | flow = %3d | capacity = %3d | is residual: %s",
          u, v, flow, capacity, isResidual());
    }

	public Edge getResidual() {
		return residual;
	}

	public void setResidual(Edge residual) {
		this.residual = residual;
	}

	public long getFlow() {
		return flow;
	}

	public void setFlow(int flow) {
		this.flow = flow;
	}

	public Vertex getFrom() {
		return from;
	}

	public void setFrom(Vertex from) {
		this.from = from;
	}

	public Vertex getTo() {
		return to;
	}

	public void setTo(Vertex to) {
		this.to = to;
	}
    
    
    
  }

	
	

