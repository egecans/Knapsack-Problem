
public class Vertex {
	
	private String type;
	private int ID, capacity;
	private static int nTotalGifts = 0;
	
	public Vertex (int ID, String type, int capacity) {
		
		this.ID=ID;
		this.type=type;
		this.capacity = capacity;
		if (type.length()<=3) //since the size of type vehicles, sink and source larger than 3 it's used for bag types
			nTotalGifts += capacity;
	}

	public String getType() {
		return type;
	}

	public int getID() {
		return ID;
	}

	public int getCapacity() {
		return capacity;
	}

	public static int getnTotalGifts() {
		return nTotalGifts;
	}
	
	public int getRemainingCapacity(int bottleneck) {
		if ( !type.equals("source") || !type.equals("sink") || type.length()>4) 
			capacity = capacity - Integer.min(bottleneck, capacity);
		return capacity;
	}

}
