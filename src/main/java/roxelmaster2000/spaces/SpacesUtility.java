package roxelmaster2000.spaces;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openspaces.admin.Admin;
import org.openspaces.admin.AdminFactory;
import org.openspaces.admin.gsm.GridServiceManager;
import org.openspaces.admin.pu.ProcessingUnit;
import org.openspaces.admin.space.SpaceDeployment;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.transaction.manager.DistributedJiniTxManagerConfigurer;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.j_spaces.core.client.SQLQuery;

import roxelmaster2000.Direction;
import roxelmaster2000.DrivingDirection;
import roxelmaster2000.pojos.Car;
import roxelmaster2000.pojos.EmptyCar;
import roxelmaster2000.pojos.Roxel;
import roxelmaster2000.pojos.Structure;

public class SpacesUtility {
	public static GigaSpace getGigaspace() {
		return DataGridConnectionUtility.getSpace("roxelmaster2000");
	}
	
	static public void initGameField(GigaSpace gs, Structure structure) {
		gs.write(structure);
		
		final int H_ROADS = 5;
		final int V_ROADS = 5;
		
        Random rand = new Random(structure.seed);
        Set<Integer> horizontals = new HashSet<Integer>();
        while(horizontals.size() < H_ROADS) {
            horizontals.add(rand.nextInt(structure.height));
        }

        Set<Integer> verticals = new HashSet<Integer>();
        while(verticals.size() < V_ROADS) {
            verticals.add(rand.nextInt(structure.width));
        }
        
        for (int y = 0; y < structure.height; y++) {
        	for (int x = 0; x < structure.width; x++) {
        		int direction = 0;
        		DrivingDirection drivingDir = DrivingDirection.TODECIDE;
        		if (horizontals.contains(y)) {
        			direction |= Direction.EAST.value();
        			drivingDir = DrivingDirection.EAST;
        		}
        		if (verticals.contains(x)) {
        			direction |= Direction.SOUTH.value();
        			drivingDir = DrivingDirection.SOUTH;
        		}
        		
        		if (direction != 0) {
        			Roxel r = new Roxel();
        			r.direction = direction;
        			r.drivingDirection = drivingDir;
        			r.x = x;
        			r.y = y;
        			r.car = new EmptyCar();
        			gs.write(r);
        		}
        	}
        }

	}
	
	static public Roxel moveCar(GigaSpace gs, Roxel roxel, int width, int height, int delta_x, int delta_y) {
		int newX = (roxel.getX() + delta_x) % width;
		int newY = (roxel.getY() + delta_y) % height;
		
		SQLQuery<Roxel> query = new SQLQuery<Roxel>(Roxel.class, "x = ? and y = ? and car.empty = true");
		query.setParameters(newX, newY);
		
		Roxel nextRoxel = gs.take(query);
		if (nextRoxel != null) {
			// write car into next roxel
			Car c = (Car) roxel.getCar();
			c.setDirection(Direction.valueOf(nextRoxel.direction));
			nextRoxel.setCar(c);
			gs.write(nextRoxel);
			
			// write empty car into current roxel
			Roxel currentRoxel = gs.takeById(Roxel.class, roxel.getId());
			currentRoxel.setCar(new EmptyCar());
			gs.write(currentRoxel);
			
			return nextRoxel;
		} else {
			return null;
		}
	}
}
