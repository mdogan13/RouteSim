import java.util.Arrays;
import java.util.HashMap;

public class Node {

	//Variables-------------------------------------------
	private int nodeID;
	private int[][] distanceTable;
	private HashMap<Integer, Integer> linkCost;
	//----------------------------------------------------
	
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

	public void receiveUpdate(Message m) {

	}

	public boolean sendUpdate() {
		return false;
	}

	public HashMap<String, Integer> getForwardingTable() {
		HashMap<String, Integer> ftable = new HashMap<String, Integer>();
		return ftable;
	}

	// HELPER METHODS/GETTERS

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