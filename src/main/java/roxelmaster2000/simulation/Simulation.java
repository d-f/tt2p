package roxelmaster2000.simulation;

import org.openspaces.core.GigaSpace;

import roxelmaster2000.pojos.Structure;
import roxelmaster2000.spaces.SpacesUtility;

public class Simulation {
	public static void main(String args[]) {
		try {
			GigaSpace gs = SpacesUtility.getGigaspace();
			
			Structure struct = new Structure();
			struct.seed = (int)System.currentTimeMillis();
			struct.width = 40;
			struct.height = 20;
			struct.roxelMeters = 20;
			
			SpacesUtility.initGameField(gs, struct);
			System.out.println("Initialized game field.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
