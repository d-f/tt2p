package roxelmaster2000.simulation;

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
				Roxel template = new Roxel();
				template.setX(x);
				template.setY(y);
				
				SQLQuery<Roxel> query = new SQLQuery<Roxel>(Roxel.class, "x = ? and y = ? and car.empty = true");
				query.setParameters(x,y);
				currentRoxel = gs.take(query);
				currentRoxel.setCar(new Car());
				dir = currentRoxel.getDirection();
				gs.write(currentRoxel);
				currentRoxel = gs.read(new SQLQuery<Roxel>(Roxel.class, "x = ? and y = ?").setParameter(1, x).setParameter(2, y));
				car = (Car)currentRoxel.getCar();
			}

			// drive through roxel
			System.out.println(this.toString() + " Drive through roxel: " + currentRoxel.getX() + ":" + currentRoxel.getY());
			sleep(1400);


			Roxel nextRoxelTemplate = nextRoxel();
			SQLQuery<Roxel> query = new SQLQuery<Roxel>(Roxel.class, "x = ? and y = ? and car.empty = true");
			query.setParameters(nextRoxelTemplate.getX(), nextRoxelTemplate.getY());

			// enter next roxel
			Roxel nextRoxel = null;
			do {
				nextRoxel = gs.take(query);
				System.out.println(this.toString() + " try to enter next roxel");
				if (nextRoxel == null) sleep(100);
			} while (nextRoxel == null);
			nextRoxel.setCar(car);
			
			// write current car into nextRoxel
			gs.write(nextRoxel);
			
			// drive into next roxel
			sleep(600);
			
			// write empty car into current roxel
			currentRoxel = gs.takeById(Roxel.class, currentRoxel.getId());
			currentRoxel.setCar(new EmptyCar());
			gs.write(currentRoxel);
			currentRoxel = nextRoxel;
		}
	}

}
