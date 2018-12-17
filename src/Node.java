import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Node {

	//Variables-------------------------------------------
	private int nodeID;
	private int[][] distanceTable;
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
		this.distanceTable = new int[tableSize][tableSize];

	}

	/**
	 * @param m Message created by sender
	 * It modifies a row of the distance table of the receiver node to the vector inside the message.
	 */
	public void receiveUpdate(Message m) {

		//If the message is coming from node 3, change 3rd row of the receiver's distance table.
		this.distanceTable[m.getSenderID()]=m.getDistanceVector();

		System.out.println("Message received: "+"Sender ID: "+m.getSenderID()+" Receiver ID: "+this.nodeID);
		//Update the receiver node's distance vector
		process();
		System.out.println("Node "+this.getNodeID()+"'s distance table is updated.");


		this.printDistanceTable();


	}

	/**
	 * Sends messages to the neighbor rows when needed
	 * @return
	 */
	public boolean sendUpdate() {

		ArrayList<Node> neighbors = this.getNeighbors();
		for(Node n: neighbors) {
			int[] distVect = this.getDistanceTable()[this.getNodeID()];
			//System.out.println(Arrays.toString(distVect));

			Message m = new Message(this.getNodeID(),n.getNodeID(),distVect);
			boolean needupdate=false;
			int[]vecttosend = this.distanceTable[m.getSenderID()];
			int[]vectofreceiver = n.getDistanceTable()[m.getSenderID()];

			if(!Arrays.equals(vecttosend,vectofreceiver)) {
				needupdate=true;
			}
			if(needupdate) {
				System.out.println("Message sent to Node "+m.getReceiverID()+" Content: "+Arrays.toString(m.getDistanceVector()));
				n.receiveUpdate(m);
				return true;
			}else {
				System.out.print("Tried sending message to Node "+m.getReceiverID()+" from Node "+this.getNodeID()+" Content: "+Arrays.toString(m.getDistanceVector()));
				System.out.println(". Node "+m.getReceiverID()+"'s distance table is not updated.");

			}

		}




		return false;
	}


	/***
	 * Processes the table after an update
	 * @return
	 */
	public void process() {


		int[][] distTable = this.getDistanceTable();
		int[] distVect = this.getDistanceTable()[this.getNodeID()];
		//boolean changed = false;

		for(int i = 0; i<distVect.length; i++) {
			
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
				
			}

		}
//		int[]oldvect=this.getDistanceTable()[this.getNodeID()];
//		int[]newvect=distVect;
//		if(!Arrays.equals(oldvect, newvect)) {
//			//changed=true;
//		}
		
		//Update the distance vector.
		this.getDistanceTable()[this.getNodeID()]=distVect;


		//System.out.println("CHANGED VAR FROM PROCESS: "+changed);
		//return changed;
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