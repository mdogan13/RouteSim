import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException {
	
		RouteSim simulation = new RouteSim("input.txt");
			simulation.startSimulation();
		}
	


}