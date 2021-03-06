import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class RouteSim {
	static ArrayList<Node> topology;
	String filename;

	public RouteSim(String filename) {
		this.filename=filename;
		topology= new ArrayList<Node>();
	}

	public void startSimulation() throws FileNotFoundException, IOException {
		//Main algorithm
		this.createTopology();
		this.initializeDistanceTables();
		this.printDistanceTables();
	}

	public static ArrayList<String> readText(String filename) throws FileNotFoundException, IOException {
		//Read file into an arraylist of lines
		File file = new File( filename );
		ArrayList<String> rawNodes = new ArrayList<String>();

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				rawNodes.add(line);
			}
		}
		return rawNodes;
	}

	public void createTopology() throws FileNotFoundException, IOException{
		ArrayList<String> rawNodes = readText(this.filename);
		//Generate Node objects from each line of the input file
		for(String l: rawNodes) {
			String[] seperatedLine = l.split(",(?![^\\(\\[]*[\\]\\)])");
			HashMap<Integer,Integer> linkCost = new HashMap<Integer,Integer>();
			for(int i = 1; i < seperatedLine.length; i++) {
				String tableEntry = seperatedLine[i];
				linkCost.put(Character.getNumericValue(tableEntry.charAt(1)), Character.getNumericValue(tableEntry.charAt(3)));
				//System.out.println("linkcost: "+linkCost);
			}
			//System.out.println("node complete");
			//System.out.println("nodeid: "+seperatedLine[0]);

			Node n = new Node (Integer.parseInt(seperatedLine[0]),linkCost,rawNodes.size());
			topology.add(n);

		}

		printTopology();

	}
	
	public void initializeDistanceTables() {
		for(Node n:topology) {
			int nodeID = n.getNodeID();
			HashMap<Integer, Integer> linkCosts = n.getLinkCost();
			//System.out.println("Initializing the distance table for node "+nodeID);
			
			int[][] distanceTable = n.getDistanceTable();

			for(int i =0; i<topology.size();i++) {
				for(int j =0; j<topology.size();j++) {	

					if(n.isNeighbor(j) && i==nodeID) {
						//System.out.println("Node number "+ nodeID+" is a neighbor of node number "+j);
						int distance = linkCosts.get(j);
						//System.out.println("The distance from node "+nodeID+" to"+" node "+ j+ " is "+distance);
						distanceTable[i][j]=distance;
					}else {
						//System.out.println("i j in else are "+nodeID+" "+j);
						distanceTable[i][j]=999;
					}

				}
			}
		}

	}


	

	//DEBUG
	
	public void printTopology() {
		System.out.println("Topology: ");
		for(Node n: topology) {
			System.out.print("Node "+n.getNodeID()+": ");
			System.out.println(n.getLinkCost());
		}
		System.out.println(" ");
	}

	public void printDistanceTables() {
		System.out.println("Distance tables: ");
		for(Node n: topology) {
			System.out.println("Node "+n.getNodeID()+":");
			n.printDistanceTable();
			System.out.println(" ");
		}
	}
	
	
	public int distanceVectorRouting(Node from, Node destination) {
		/**
		 * dxY  = min{cxv + dvy} --- d here is the min of the costs
		 */
		
		
		
		
		return -1;
	}
	
	public int c(Node node, Node neighbor) {
		
		return -1;
	}
	
	public int d(Node neighbor, Node destination) {
		
		
		return -1;
	}


}