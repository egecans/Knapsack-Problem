import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;




public class yenimain {
	
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
		
		Scanner nReader = new Scanner(new File("src/input_9.txt"));
		Scanner reader = new Scanner(new File("src/input_9.txt"));
		PrintStream writer = new PrintStream(new File("src/output.txt"));
		
		List<Vertex> vertices = new ArrayList<Vertex>();
		int nVehicles = 0, nGreenTrains=0, nRedTrains = 0, nGreenReindeers = 0, nRedReindeers = 0;
		int ID = 2, n=2; // source 0 , sink 1   //arrayList bags index = ID-nVehicles-2
		
		
		//reading for N to construct the network
		for (int i = 2; i<11 ;i+=2) {
			readNumb numbReader = new readNumb();
			String numbLine = nReader.nextLine().trim();
			if (numbReader.getNumb(numbLine)!=-1)
				n += numbReader.getNumb(numbLine);
			nReader.nextLine();
		}
		nReader.close();
		
		Dinics nw = new Dinics(n,0,1);
		Vertex source = new Vertex (0, "source" , Integer.MAX_VALUE);
		Vertex sink = new Vertex (1, "sink" , Integer.MAX_VALUE);
		vertices.add(source);
		vertices.add(sink);
		
		
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
				
				if (bagType.contains("bc") || bagType.contains("de")) { // checking invalid bags
					n--;
					continue;	
				}
				
				
				int nGifts = bagCap;
				Vertex currBag = new Vertex (ID++, bagType, nGifts);
				vertices.add(currBag);
				nw.addEdge(source, currBag, nGifts);
				
					
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
							
					
					if (bagType.equals("bd")) {
						for (int j = 2; j<nGreenTrains+2; j++) 
							nw.addEdge(currBag, vertices.get(j), bagCap);
					}
					
					if (bagType.equals("be")) {
						for (int j = nGreenTrains+nRedTrains+2; j<nVehicles-nRedReindeers+2; j++) 
							nw.addEdge(currBag, vertices.get(j), bagCap);
					}
					
					if (bagType.equals("cd")) {
						for (int j = 2+nGreenTrains; j<nGreenTrains+nRedTrains+2; j++) 
							nw.addEdge(currBag, vertices.get(j), bagCap);
					}
					
					if (bagType.equals("ce")){ //creating edges for ce type bags
						for (int j = 2+nVehicles-nRedReindeers; j<nVehicles+2; j++) 
							nw.addEdge(currBag, vertices.get(j), bagCap);						
					}
					
					if (bagType.equals("b")){ //creating edges for b type bags
						
						for (int j = 2; j<nGreenTrains+2; j++) 
							nw.addEdge(currBag, vertices.get(j), bagCap);
						for (int j = nGreenTrains+nRedTrains+2; j<nVehicles-nRedReindeers+2; j++) 
							nw.addEdge(currBag, vertices.get(j), bagCap);
					}
					
					if (bagType.equals("c")) { //creating edges for c type bags
						
						for (int j = 2+nGreenTrains; j<nGreenTrains+nRedTrains+2; j++) 
							nw.addEdge(currBag, vertices.get(j), bagCap);
						for (int j = 2+nVehicles-nRedReindeers; j<nVehicles+2; j++) 
							nw.addEdge(currBag, vertices.get(j), bagCap);
					}
					
					if (bagType.equals("d")) { //creating edges for d type bags
						
						for (int j = 2; j<nGreenTrains+2; j++) 
							nw.addEdge(currBag, vertices.get(j), bagCap);
						for (int j = 2+nGreenTrains; j<nGreenTrains+nRedTrains+2; j++) 
							nw.addEdge(currBag, vertices.get(j), bagCap);
					}
					
					if (bagType.equals("e")) { //creating edges for e type bags
						
						for (int j = 2+nVehicles-nRedReindeers; j<nVehicles+2; j++) 
							nw.addEdge(currBag, vertices.get(j), bagCap);
						for (int j = nGreenTrains+nRedTrains+2; j<nVehicles-nRedReindeers+2; j++) 
							nw.addEdge(currBag, vertices.get(j), bagCap);
					}
							
			}   //for loop
		}   //-1 li
			reader.close();

			System.out.println(Vertex.getnTotalGifts()-nw.getMaxFlow());
		

	}

}
