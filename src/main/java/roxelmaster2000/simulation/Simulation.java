package roxelmaster2000.simulation;

import java.util.*;

import org.openspaces.core.GigaSpace;

import com.j_spaces.core.client.SQLQuery;

import roxelmaster2000.Direction;
import roxelmaster2000.pojos.Car;
import roxelmaster2000.pojos.Roxel;
import roxelmaster2000.pojos.Structure;
import roxelmaster2000.spaces.SpacesUtility;

public class Simulation {
	public final static int NUMBER_OF_CARS = 10;
	
	public static List<Thread> carThreads = new ArrayList<Thread>();
	
	public static void main(String args[]) {
		try {
			GigaSpace gs = SpacesUtility.getGigaspace();
			
			Structure struct = new Structure();
			struct.seed = (int)System.currentTimeMillis();
			struct.width = 40;
			struct.height = 20;
			struct.roxelMeters = 20;
			
			System.out.print("Initialize game field...");
			SpacesUtility.initGameField(gs, struct);
			System.out.println("done.");
			
			System.out.print("Fetching roxels...");
			ArrayList<Roxel> roxels = new ArrayList<Roxel>(Arrays.asList(gs.readMultiple(new SQLQuery<Roxel>(Roxel.class, ""))));
			System.out.println("done.");
			
			
			System.out.print("Preparing cars...");
			Random rnd = new Random(struct.seed);
			for (int n = 0; n < NUMBER_OF_CARS; n++) {
				Roxel rox = roxels.remove(rnd.nextInt(roxels.size()));
				if ((rox.getDirection() & Direction.EAST.value() & Direction.SOUTH.value()) != 0) {
					n--;
					continue;
				}
				carThreads.add(new Thread(new CarRunner(rox.getX(), rox.getY(), struct.getHeight(), struct.getWidth())));
			}
			
			// manual car
			Roxel manualRoxel = roxels.remove(rnd.nextInt(roxels.size()));
			
			SQLQuery<Roxel> query = new SQLQuery<Roxel>(Roxel.class, "x = ? and y = ? and car.empty = true");
			query.setParameters(manualRoxel.getX(), manualRoxel.getY());
			manualRoxel = gs.take(query);
			Car car = new Car();
			car.setId("manual");
			manualRoxel.setCar(car);
			gs.write(manualRoxel);
			System.out.println("Set manual car to " + manualRoxel.getX() + ":" + manualRoxel.getY());
			
			System.out.println("done.");
			
			System.out.print("Starting cars...");
			for (Thread t : carThreads) {
				t.start();
			}
			System.out.println("done.");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
