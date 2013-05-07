package roxelmaster2000.simulation;

import java.util.Random;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.transaction.manager.DistributedJiniTxManagerConfigurer;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.gigaspaces.internal.client.cache.ISpaceCache;
import com.gigaspaces.query.ISpaceQuery;
import com.j_spaces.core.client.SQLQuery;

import roxelmaster2000.Direction;
import roxelmaster2000.pojos.Car;
import roxelmaster2000.pojos.EmptyCar;
import roxelmaster2000.pojos.Roxel;
import roxelmaster2000.spaces.SpacesUtility;

public class CarRunner implements Runnable {
	Roxel currentRoxel;
	
	int x;
	int y;
	int height;
	int width;
	int dir;
	
	Car car;
	
	static int ctr = 0;
	
	synchronized private int getCounter() {
		return ctr++;
	}
	
	// x/y for starting coordinates, height/width for game field dimensions
	public CarRunner(int x, int y, int height, int width) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
	}
	
	private Roxel nextRoxel() {
		if ((dir & Direction.SOUTH.value()) != 0) {
			int newY = (currentRoxel.getY() + 1) % height;
			Roxel temp = new Roxel();
			temp.setX(currentRoxel.getX());
			temp.setY(newY);
			return temp;
		} else {
			int newX = (currentRoxel.getX() + 1) % width;
			Roxel temp = new Roxel();
			temp.setX(newX);
			temp.setY(currentRoxel.getY());
			return temp;
		}
	}
	
	static public void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// Whatever
		}
	}
	
	@Override
	public void run() {
		GigaSpace gs = SpacesUtility.getGigaspace();
		for (;;) {
			if (currentRoxel == null) {				
				SQLQuery<Roxel> query = new SQLQuery<Roxel>(Roxel.class, "x = ? and y = ? and car.empty = true");
				query.setParameters(x,y);
				do {
					currentRoxel = gs.take(query);
					sleep(100);
				} while (currentRoxel == null);
				
				car = new Car();
				car.setId(new Long(getCounter()).toString());
				currentRoxel.setCar(car);
				dir = currentRoxel.getDirection();
				System.out.println("Roxel Direction: " + currentRoxel.getDirection());
				car.setDirection(Direction.valueOf(dir));
				System.out.println("Direction: " + car.getDirection());
				gs.write(currentRoxel);
				currentRoxel = null;
				do {
					currentRoxel = gs.read(new SQLQuery<Roxel>(Roxel.class, "x = ? and y = ?").setParameter(1, x).setParameter(2, y));
					sleep(333);
				} while (currentRoxel == null);
				car = (Car)currentRoxel.getCar();
			}

			// drive through roxel
			System.out.println("Car[id=" + car.getId() + "] " + " Drive through roxel: " + currentRoxel.getX() + ":" + currentRoxel.getY());
			sleep(1200);


			Roxel nextRoxelTemplate = nextRoxel();
			SQLQuery<Roxel> query = new SQLQuery<Roxel>(Roxel.class, "x = ? and y = ? and car.empty = true");
			query.setParameters(nextRoxelTemplate.getX(), nextRoxelTemplate.getY());

			// enter next roxel
			Roxel nextRoxel;
			for (;;) {
				nextRoxel = null;
				do {
					nextRoxel = gs.take(query);
					//System.out.println(this.toString() + " try to enter next roxel");
					if (nextRoxel == null) sleep(10);
				} while (nextRoxel == null);
				
				if (nextRoxel.getDrivingDirection() != car.getDirection().value()) {
					System.out.println("Car[id=" + car.getId() + "] " + "wrong direction. expected " + car.getDirection().value() + " but was " + nextRoxel.getDrivingDirection());
					gs.write(nextRoxel);
					sleep(333);
					continue;
				}
				break;
			}
			
			nextRoxel.setCar(car);
			
			// write current car into nextRoxel
			gs.write(nextRoxel);
			
			// drive into next roxel
			sleep(600);
			
			// write empty car into current roxel
			currentRoxel = gs.takeById(Roxel.class, currentRoxel.getId());
			currentRoxel.setCar(new EmptyCar());
			if (currentRoxel.getDirection() == 10) {
				currentRoxel.setDrivingDirection(Direction.TODECIDE.value());
			}
			gs.write(currentRoxel);			
			currentRoxel = nextRoxel;
		}
	}

}
