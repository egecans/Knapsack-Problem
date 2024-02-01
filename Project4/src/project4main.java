import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;


public class project4main {
	
	public static class readNumb{
		
		
		
		public readNumb() {
			
		}
		
		 static int getNumb(String line) {
			 if (!line.equals(""))
				 return Integer.parseInt(line);
			 else
				 return -1;
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		Locale.setDefault(new Locale("en", "US"));
		
		Scanner nReader = new Scanner(new File("src/input_2.txt"));
		Scanner reader = new Scanner(new File("src/input_2.txt"));
		PrintStream writer = new PrintStream(new File("src/output.txt"));
		
//		List<Vehicle> greenTrains = new ArrayList<Vehicle>();
//		List<Vehicle> redTrains = new ArrayList<Vehicle>();
//		List<Vehicle> greenReindeers = new ArrayList<Vehicle>();
//		List<Vehicle> redReindeers = new ArrayList<Vehicle>();
//		List<Bag> aTypeBags = new ArrayList<Bag>();
		List<Vertex> vertices = new ArrayList<Vertex>();
		int bdType = 0, beType = 0, cdType = 0, ceType = 0, bType = 0, cType = 0, dType = 0, eType = 0, aType = 0;  //bag types
		int nVehicles = 0, remainingGifts, nGreenTrains=0, nRedTrains = 0, nGreenReindeers = 0, nRedReindeers = 0;
		int ID = 2; // source 0 , sink 1   //arrayList bags index = ID-nVehicles-2
		int n = 0;
		
		
		//reading for N to construct the network
		for (int i = 2; i<9 ;i+=2) {
			readNumb numbReader = new readNumb();
			String numbLine = nReader.nextLine().trim();
			if (numbReader.getNumb(numbLine)!=-1)
				n += numbReader.getNumb(numbLine);
			nReader.nextLine();
		}
		
		readNumb nRead = new readNumb();
		int splitTime = nRead.getNumb(nReader.nextLine()) * 2;
		if (splitTime != -2) {
			String split[] = nReader.nextLine().split(" " , splitTime );
						
				for (int i = 0; i<splitTime; i+=2) {
							
					if (split[i].startsWith("a")) 
						n += 1; 
				}				
		}
		
		nReader.close();
		n += 2;
		System.out.println(n);
		Dinics nw = new Dinics(n,0,1);
		Vertex source = new Vertex (0, "source" , Integer.MAX_VALUE);
		Vertex sink = new Vertex (1, "sink" , Integer.MAX_VALUE);
		vertices.add(source);
		vertices.add(sink);
		
		
		//reading vehicles
		
		
		for (int i = 2; i<9 ; i+=2) {
			
			readNumb numbReader = new readNumb();
			String numbLine = reader.nextLine().trim();
			String line = reader.nextLine().trim();
			if (numbReader.getNumb(numbLine) != -1) {
				String split[] = line.split(" ",numbReader.getNumb(numbLine));
				nVehicles += numbReader.getNumb(numbLine);
				for (String elem : split) {
					if (i == 2) {
						nGreenTrains += 1;
						Vertex currTrain = new Vertex (ID++, "greenTrain",Integer.parseInt(elem));
						vertices.add(currTrain);
						nw.addEdge(currTrain, sink, Integer.parseInt(elem));
					}
					if (i == 4) {
						nRedTrains += 1;
						Vertex currTrain = new Vertex (ID++, "redTrain",Integer.parseInt(elem));
						vertices.add(currTrain);
						nw.addEdge(currTrain, sink, Integer.parseInt(elem));
					}
					if (i == 6) {
						nGreenReindeers += 1;
						Vertex currReindeer = new Vertex (ID++, "greenReindeer",Integer.parseInt(elem));
						vertices.add(currReindeer);
						nw.addEdge(currReindeer, sink, Integer.parseInt(elem));
					}
					if (i == 8) {
						nRedReindeers++;
						Vertex currReindeer = new Vertex (ID++, "redReindeer",Integer.parseInt(elem));
						vertices.add(currReindeer);
						nw.addEdge(currReindeer, sink, Integer.parseInt(elem));
					}
				}
			}
		}
		
		readNumb numbReader = new readNumb();
		String numbLine = reader.nextLine().trim();
		int number = numbReader.getNumb(numbLine);
		String line = reader.nextLine().trim();
		if (number != -1 ) {

			String split[] = line.split(" " , number*2 );
			
			for (int i = 0; i<number*2; i+=2) {
				
				String bagType = split[i];
				int bagCap = Integer.parseInt(split[i+1]);
				
				if (bagType.contains("bc") || bagType.contains("de")) // checking invalid bags 
					continue;
				

				if (bagType.startsWith("a")) { //checking bags which distribute seperately
					int nGifts = bagCap;				
					Vertex currBag = new Vertex (ID++, "bag", nGifts);
					vertices.add(currBag);
					nw.addEdge(source, currBag, nGifts);
					aType+=nGifts;
					
					if (bagType.equals("abd")) { //creating edges for abd type bags
						
						for (int j = 2; j<nGreenTrains+2; j++) 
							nw.addEdge(currBag, vertices.get(j), 1);
					}
						
					if (bagType.equals("abe")) { //creating edges for abe type bags

						for (int j = nGreenTrains+nRedTrains+2; j<nVehicles-nRedReindeers+2; j++) 
							nw.addEdge(currBag, vertices.get(j), 1);
					}
					
					if (bagType.equals("acd")) { //creating edges for acd type bags
						
						for (int j = 2+nGreenTrains; j<nGreenTrains+nRedTrains+2; j++) 
							nw.addEdge(currBag, vertices.get(j), 1);
					}
					
					if (bagType.equals("ace")){ //creating edges for ace type bags
						
						for (int j = 2+nVehicles-nRedReindeers; j<nVehicles+2; j++) 
							nw.addEdge(currBag, vertices.get(j), 1);						
					}
				
					if (bagType.equals("ab")){ //creating edges for ab type bags
						
						for (int j = 2; j<nGreenTrains+2; j++) 
							nw.addEdge(currBag, vertices.get(j), 1);
						for (int j = nGreenTrains+nRedTrains+2; j<nVehicles-nRedReindeers+2; j++) 
							nw.addEdge(currBag, vertices.get(j), 1);
					}
						
					if (bagType.equals("ac")) { //creating edges for ac type bags
						
						for (int j = 2+nGreenTrains; j<nGreenTrains+nRedTrains+2; j++) 
							nw.addEdge(currBag, vertices.get(j), 1);
						for (int j = 2+nVehicles-nRedReindeers; j<nVehicles+2; j++) 
							nw.addEdge(currBag, vertices.get(j), 1);
					}
					
					if (bagType.equals("ad")) { //creating edges for ad type bags
						
						for (int j = 2; j<nGreenTrains+2; j++) 
							nw.addEdge(currBag, vertices.get(j), 1);
						for (int j = 2+nGreenTrains; j<nGreenTrains+nRedTrains+2; j++) 
							nw.addEdge(currBag, vertices.get(j), 1);
					}	
					
					if (bagType.equals("ae")) { //creating edges for ae type bags
						
						for (int j = 2+nVehicles-nRedReindeers; j<nVehicles+2; j++) 
							nw.addEdge(currBag, vertices.get(j), 1);
						for (int j = nGreenTrains+nRedTrains+2; j<nVehicles-nRedReindeers+2; j++) 
							nw.addEdge(currBag, vertices.get(j), 1);
					}	
					if (bagType.equals("a")) { //creating edges for a type bags
						
						for (int j = 2; j<nVehicles+2 ; j++)
							nw.addEdge(currBag, vertices.get(j), 1);
					}
							
			}
						
				if (bagType.equals("bd")) {
					System.out.println("bd: " + bagCap);
					bdType += bagCap;
				}
				
				if (bagType.equals("be"))
					beType += bagCap;
				if (bagType.equals("cd"))
					cdType += bagCap;
				if (bagType.equals("ce"))	
					ceType += bagCap;
				if (bagType.equals("b"))
					bType += bagCap;
				if (bagType.equals("c"))
					cType += bagCap;
				if (bagType.equals("d"))	
					dType += bagCap;
				if (bagType.equals("e"))	
					eType += bagCap;
			}
		}
			reader.close();
			
			ID--;
			System.out.println("a max flow: " + nw.getMaxFlow());
			
			int totalGreenTrainCap = 0;
			int totalRedTrainCap = 0;
			int totalGreenReindeerCap = 0;
			int totalRedReindeerCap = 0;
			
			
			for (int j = 2; j<nGreenTrains+2; j++) 
				totalGreenTrainCap += vertices.get(j).getCapacity();
			
			
			for (int j = 2+nGreenTrains; j<nGreenTrains+nRedTrains+2; j++) 
				totalRedTrainCap += vertices.get(j).getCapacity();
			
			
			for (int j = nGreenTrains+nRedTrains+2; j<nVehicles-nRedReindeers+2; j++) 
				totalGreenReindeerCap += vertices.get(j).getCapacity();
			
			
			for (int j = 2+nVehicles-nRedReindeers; j<nVehicles+2; j++)	
				totalRedReindeerCap += vertices.get(j).getCapacity();

			int reachedbd, reachedcd, reachedbe, reachedce;
			reachedbd = Integer.min(totalGreenReindeerCap, bdType);
			reachedcd = Integer.min(totalRedTrainCap, cdType);
			reachedbe = Integer.min(totalGreenReindeerCap, beType); 
			reachedce = Integer.min(totalRedReindeerCap, ceType);
			
			totalGreenTrainCap = totalGreenTrainCap - reachedbd;
			totalRedTrainCap = totalRedTrainCap - reachedcd;
			totalGreenReindeerCap = totalGreenReindeerCap - reachedbe;
			totalRedReindeerCap = totalRedReindeerCap - reachedce;
			
			Dinics nw2 = new Dinics(10,0,1);
			List<Vertex> vertices2 = new ArrayList<Vertex>();
			Vertex source2 = new Vertex(0,"source",Integer.MAX_VALUE);
			Vertex sink2 = new Vertex(1,"sink",Integer.MAX_VALUE);
			vertices2.add(source2);
			vertices2.add(sink2);
			vertices2.add(new Vertex(2,"greenTrain",totalGreenTrainCap));
			vertices2.add(new Vertex(3,"redTrain",totalRedTrainCap));
			vertices2.add(new Vertex(4,"greenReindeer",totalGreenReindeerCap));
			vertices2.add(new Vertex(5,"redReindeer",totalRedReindeerCap));
			vertices2.add(new Vertex(6,"bag",bType));
			vertices2.add(new Vertex(7,"bag",cType));
			vertices2.add(new Vertex(8,"bag",dType));
			vertices2.add(new Vertex(9,"bag",eType));
			
			for (int k = 2; k<6 ; k++) {
				Vertex currVehicle = vertices2.get(k);
				Vertex currBag = vertices2.get(k+4);
				nw2.addEdge(source2, currBag, currBag.getCapacity());
				nw2.addEdge(currVehicle, sink2, currVehicle.getCapacity());
			}
			
			nw2.addEdge(vertices2.get(6), vertices2.get(2), vertices2.get(6).getCapacity());
			nw2.addEdge(vertices2.get(6), vertices2.get(4), vertices2.get(6).getCapacity());
			nw2.addEdge(vertices2.get(7), vertices2.get(3), vertices2.get(7).getCapacity());
			nw2.addEdge(vertices2.get(7), vertices2.get(5), vertices2.get(7).getCapacity());
			nw2.addEdge(vertices2.get(8), vertices2.get(2), vertices2.get(8).getCapacity());
			nw2.addEdge(vertices2.get(8), vertices2.get(3), vertices2.get(8).getCapacity());
			nw2.addEdge(vertices2.get(9), vertices2.get(4), vertices2.get(9).getCapacity());
			nw2.addEdge(vertices2.get(9), vertices2.get(5), vertices2.get(9).getCapacity());

			
			
			remainingGifts = Vertex.getnTotalGifts()-nw.getMaxFlow()-nw2.getMaxFlow()+bdType+beType+cdType+ceType-reachedbd-reachedbe-reachedce-reachedcd;
			System.out.println("nw2 maxflow: " + nw2.getMaxFlow());
			System.out.println("remaining: " + remainingGifts);
			
//			for (int s = 0; s<n ;s++) {
//				
//				for (int i = 0; i<nw.getGraph()[s].size() ; i++) 
//					System.out.println(nw.getGraph()[s].get(i).toString(source, sink));
//				
//			}
			
			
			
			
			
			
			
		
			
			
			
			
			
			
			
			
			
			
			
//			System.out.println("nVeh: "+ nVehicles);
//			System.out.println("aType: " + aType);
//			System.out.println("ID: " + ID)
//			System.out.println("b type: " +bType);
//			System.out.println("c type: " +cType);
//			System.out.println("d type: " +dType);
//			System.out.println("e type: " +eType);
//			System.out.println("bd type: " +bdType);
			
//			System.out.println("be type: " +beType);
//			System.out.println("cd type: " +cdType);
//			System.out.println("ce type: " +ceType);
			
			
//			for (int i = 2; i<nVehicles+2; i++) {
//				if (i < 2+nGreenTrains)
//				System.out.println(vertices.get(i).getType());
//				else if (i < 2+nGreenTrains+nRedTrains)
//					System.out.println(vertices.get(i).getType());
//				else if (i < 2+nGreenTrains+nRedTrains+nGreenReindeers)
//					System.out.println(vertices.get(i).getType());
//				else
//					System.out.println(vertices.get(i).getType());
//			}
//				
//			for (int i = nVehicles+2 ; i < n ; i++ )
//				System.out.println(vertices.get(i).getType());
//			
//			String xy = "adsg";
//			while (true) {
//				
//				if (xy.contains("ad"))
//					continue;
//				if (xy.contains("s"))
//					System.out.println("s");
//				
//				break;
//			}
		
		//reading Bags
		

		
		
//		int number;
//		if (!numbLine.equals("")) {
//			number = Integer.parseInt(numbLine.trim());
//			System.out.println(number);
//		}
//		reader.nextLine();
//		numbLine = reader.nextLine();
//		number = Integer.parseInt(numbLine.trim());
//		System.out.println(number);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//		new Bag (0,"xsx",3);
//		new Bag (1,"das",5);
//		
//		System.out.println(Bag.getnTotalGifts());
//		
//		List<Integer> lol = new ArrayList<Integer>();
//		lol.add(1);
//		lol.add(2);
//		for (int elem: lol) 
//			System.out.println(elem);
		
		
		
		
		
		
		
		
//		int n = 11;
//	    int s = n - 1;
//	    int t = n - 2;
//
//	    Network solver;
//	    solver = new Dinics(n, s, t);
//
//	    // Source edges
//	    solver.addEdge(s, 0, 5);
//	    solver.addEdge(s, 1, 10);
//	    solver.addEdge(s, 2, 15);
//
//	    // Middle edges
//	    solver.addEdge(0, 3, 10);
//	    solver.addEdge(1, 0, 15);
//	    solver.addEdge(1, 4, 20);
//	    solver.addEdge(2, 5, 25);
//	    solver.addEdge(3, 4, 25);
//	    solver.addEdge(3, 6, 10);
//	    solver.addEdge(4, 2, 5);
//	    solver.addEdge(4, 7, 30);
//	    solver.addEdge(5, 7, 20);
//	    solver.addEdge(5, 8, 10);
//	    solver.addEdge(7, 8, 15);
//
//	    // Sink edges
//	    solver.addEdge(6, t, 5);
//	    solver.addEdge(7, t, 15);
//	    solver.addEdge(8, t, 10);
//
//	    // Prints: "Maximum flow: 30"
//	    System.out.printf("Maximum flow: %d\n", solver.getMaxFlow());
	  }


}
