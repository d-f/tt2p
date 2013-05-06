package roxelmaster2000.simulation;

import java.awt.RadialGradientPaint;
import java.util.Random;

import org.openspaces.core.GigaSpace;

import com.j_spaces.core.client.SQLQuery;

import roxelmaster2000.Direction;
import roxelmaster2000.DrivingDirection;
import roxelmaster2000.pojos.Car;
import roxelmaster2000.pojos.Roxel;
import roxelmaster2000.pojos.Structure;

public class TrafficLight implements Runnable {
	private GigaSpace gs;
	private Structure struct;
	private int foresight;
	private Random rnd = new Random(System.currentTimeMillis());
	
	public TrafficLight(GigaSpace gs, Structure struct) {
		this.gs = gs;
		this.struct = struct;
		foresight = 4;
	}
	
	public int carsComingFromNorth(Roxel r) {
		int cars = 0;
		Roxel template = new Roxel();
		template.setX(r.getX());
		for (int n = 1; n <= foresight; n++) {
			template.setY((r.getY() + struct.height - n) % struct.height);
			Roxel queueRox = gs.read(template);
			if (queueRox.getCar().getEmpty() == false) {
				Car c = (Car)queueRox.getCar();
				if (c != null && (c.getDirection().equals(Direction.SOUTH))) {
					cars++;
				}
			}
		}
		
		return cars;
	}
	
	public int carsComingFromWest(Roxel r) {
		int cars = 0;
		Roxel template = new Roxel();
		template.setY(r.getY());
		for (int n = 1; n <= foresight; n++) {
			template.setX((r.getX() + struct.width - n) % struct.width);
			Roxel queueRox = gs.read(template);
			if (queueRox.getCar().getEmpty() == false) {
				Car c = (Car)queueRox.getCar();
				if (c != null && (c.getDirection().equals(Direction.EAST))) {
					cars++;
				}
			}
		}
		
		return cars;
	}
	
	public Roxel eventListener(Roxel r) {
		
		int carQueueLenNorth = carsComingFromNorth(r);
		int carQueueLenWest = carsComingFromWest(r);
		
		if (carQueueLenNorth > carQueueLenWest) {
			r.setDrivingDirection(DrivingDirection.SOUTH);
		} else if (carQueueLenNorth < carQueueLenWest) {
			r.setDrivingDirection(DrivingDirection.EAST);
		} else {
			r.setDrivingDirection(rnd.nextBoolean() ? DrivingDirection.SOUTH : DrivingDirection.EAST);
		}
		System.out.println("Write back: " + r);
		return r;
	}
	
	
	
	@Override
	public void run() {
		Roxel template = new Roxel();
        template.drivingDirection = DrivingDirection.TODECIDE;
        
		for (;;) {
			Roxel result = null;
			do {
				result = gs.take(template);
				if (result == null) {
					CarRunner.sleep(100);
				}
			} while (result == null);
			System.out.println("YAY: " + result.getDrivingDirection());
			
			gs.write(eventListener(result));
		}
	}
	
}
