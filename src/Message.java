
public class Message {
	//Variables-------------------------------------------
	private int VECTOR_SIZE;
	private int[] distanceVector;
	private int senderID;
	private int receiverID;
	//----------------------------------------------------

	public Message(int senderID,int receiverID,int[] distanceVector) {
		this.senderID = senderID;
		this.receiverID = receiverID;
		this.distanceVector = distanceVector;
	}

	public int[] getDistanceVector() {
		return this.distanceVector;
	}

	public int getSenderID() {
		return this.senderID;
	}
	public int getReceiverID() {
		return this.receiverID;
	}
}
