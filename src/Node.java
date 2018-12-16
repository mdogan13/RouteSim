import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Node {

	//Variables-------------------------------------------
	private int nodeID;
	private int[][] distanceTable;
	private boolean anythingNew;
	private HashMap<Integer, Integer> linkCost;
	private int[][] ref;
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
		boolean changed=false;
		int[]oldvect = this.distanceTable[m.getSenderID()];
		int[]newvect = m.getDistanceVector();
		if(!Arrays.equals(oldvect,newvect)) {
			System.out.println(oldvect.toString());
			System.out.println(newvect.toString());
			changed=true;
		}
		//If the message is coming from node 3, change 3rd row of the receiver's distance table.
		this.distanceTable[m.getSenderID()]=m.getDistanceVector();
	
		System.out.println(this.nodeID+": Message received from "+m.getSenderID()+": "+m.toString());
		
		if(process()||changed) {
			//Table is updated
			System.out.println("Node "+this.getNodeID()+"'s distance table is updated.");
			//Will start counting for convergence from the beginning.
			RouteSim.convergenceCounter=0;
		}else {
			System.out.println("Node "+this.getNodeID()+"'s distance table is not updated.");
		}
		
		this.printDistanceTable();
		

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
			//int pointer = 0;
			int oldDist = distVect[i];
			int newDist = 0;
			for(int j = 0; j<distVect.length;j++) {
				
				int c = distTable[this.getNodeID()][j];
				int d = distTable[j][i];
				
				newDist = c+d;
				
				if(newDist<oldDist) {
					distVect[i] = newDist;
					oldDist = newDist;
					
				}
				//pointer ++;
			}
			
		}
		int[]oldvect=this.getDistanceTable()[this.getNodeID()];
		int[]newvect=distVect;
		if(!Arrays.equals(oldvect, newvect)) {
			changed=true;
		}
		this.getDistanceTable()[this.getNodeID()]=distVect;
		
	
		//System.out.println("CHANGED VAR FROM PROCESS: "+changed);
		return changed;
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