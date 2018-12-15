import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Node {

	//Variables-------------------------------------------
	private int nodeID;
	private int[][] distanceTable;
	private boolean anythingNew;
	private HashMap<Integer, Integer> linkCost;
	//Variables-------------------------------------------
	
	/***
	 * 
	 * @param nodeID
	 * @param linkCost
	 * @param tableSize
	 */
	public Node(int nodeID, HashMap<Integer, Integer> linkCost, int tableSize) {
		this.nodeID = nodeID;
		this.linkCost = linkCost;
		this.anythingNew = false;
		this.distanceTable = new int[tableSize][tableSize];

	}

	/**
	 * @param m Message created by sender
	 * It modifies a row of the distance table of the receiver node to the vector inside the message.
	 */
	public void receiveUpdate(Message m) {
		//If the message is coming from node 3, change 3rd row of the receiver's distance table.
		this.distanceTable[m.getSenderID()]=m.getDistanceVector();
		System.out.println(this.nodeID+": Message received from "+m.getSenderID()+": "+m.toString());
		process();

	}

	/**
	 * Sends messages to the neighbor rows when needed
	 * @return
	 */
	public boolean sendUpdate() {
		//temporary boolean for imitating convergence
		boolean convergence = false;
		
		if(!convergence) {
			
			ArrayList<Node> neighbors = this.getNeighbors();
			for(Node n: neighbors) {
				int[] distVect = this.getDistanceTable()[this.getNodeID()];
				//System.out.println(Arrays.toString(distVect));
				Message m = new Message(this.getNodeID(),n.getNodeID(),distVect);
				System.out.println(m.toString());
				n.receiveUpdate(m);
			}
			return true;
		}
		
		
		return false;
	}
	
	
	/***
	 * Processes the table after an update
	 * @return
	 */
	public boolean process() {
		
		
		int[][] distTable = this.getDistanceTable();
		int[] distVect = this.getDistanceTable()[this.getNodeID()];
		boolean changed = false;
		
		for(int i = 0; i<distVect.length; i++) {
			int pointer = 0;
			int oldDist = distVect[i];
			int newDist = 0;
			for(int j = 0; j<distVect.length;j++) {
				
				int c = distTable[this.getNodeID()][j];
				int d = distTable[j][pointer];
				
				newDist = c+d;
				
				if(newDist<oldDist) {
					distVect[i] = newDist;
					oldDist = newDist;
					changed = true;
				}
				pointer ++;
			}
			
		}
		
		this.getDistanceTable()[this.getNodeID()]=distVect;
		if(changed) {
			this.sendUpdate();
		}
				
		return false;
	}

	public HashMap<String, Integer> getForwardingTable() {
		HashMap<String, Integer> ftable = new HashMap<String, Integer>();
		return ftable;
	}

	// HELPER METHODS/GETTERS
	
	
	/**
	 * @returns the neighbors of the node in an ArrayList
	 */
	public ArrayList<Node> getNeighbors(){
		
		ArrayList<Node> neighbors = new ArrayList<Node>();
		
		for(Node n: RouteSim.topology) {
			if(this.isNeighbor(n.getNodeID())) {
				neighbors.add(n);
			}
		}
		return neighbors;
	}

	public boolean isNeighbor(int nodeID) {
		if (this.linkCost.containsKey(nodeID)) {
			return true;
		}
		return false;
	}

	public int[][] getDistanceTable() {
		return this.distanceTable;
	}

	public int getNodeID() {
		return nodeID;
	}

	public HashMap<Integer, Integer> getLinkCost() {
		return linkCost;
	}

	public void printDistanceTable() {
		System.out.println(
				Arrays.deepToString(distanceTable).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
	}
}