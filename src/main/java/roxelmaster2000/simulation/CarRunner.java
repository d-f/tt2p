package roxelmaster2000.simulation;

import org.openspaces.core.GigaSpace;

import com.j_spaces.core.client.SQLQuery;

import roxelmaster2000.Direction;
import roxelmaster2000.pojos.Roxel;
import roxelmaster2000.spaces.SpacesUtility;

public class CarRunner implements Runnable {
	Roxel currentRoxel;
	
	int x;
	int y;
	int height;
	int width;
	int dir;
	
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
	
	@Override
	public void run() {
		GigaSpace gs = SpacesUtility.getGigaspace();
		
		for (;;) {
			if (currentRoxel == null) {
				Roxel template = new Roxel();
				template.setX(x);
				template.setY(y);
				System.out.println("Query: " + template);
				currentRoxel = gs.take(template);
				dir = currentRoxel.getDirection();
			}

			// drive through roxel
			System.out.println(this.toString() + " Drive through roxel: " + currentRoxel.getX() + ":" + currentRoxel.getY());
			try {
				Thread.sleep(1400);
			} catch (InterruptedException e) {
				// Whatever
			}

			Roxel nextRoxel = gs.take(nextRoxel());

			// drive into next roxel
			try {
				Thread.sleep(600);
			} catch (InterruptedException e) {
				// Whatever
			}
			gs.write(currentRoxel);
			currentRoxel = nextRoxel;
		}
	}

}
