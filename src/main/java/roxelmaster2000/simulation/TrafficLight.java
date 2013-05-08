package roxelmaster2000.simulation;

import java.awt.RadialGradientPaint;
import java.util.Random;

import org.openspaces.core.GigaSpace;

import com.j_spaces.core.client.SQLQuery;

import roxelmaster2000.Direction;
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
		foresight = 5;
	}
	
	public int preview(Roxel r, boolean directionSouth) {
		int count = 0;
		
		for (int n = 1; n < foresight; n++) {
			Roxel previewTemplate = new Roxel();
			previewTemplate.setX(((r.getX() + struct.width) - (directionSouth ? 0 : n)) % struct.width);
			previewTemplate.setY(((r.getY() + struct.height) - (!directionSouth ? 0 : n)) % struct.height);
			Roxel preview = null;
			while (preview == null) {
				preview = gs.read(previewTemplate);
				CarRunner.sleep(33);
				if (preview == null) {
					System.out.println("Missing roxel, above " + r + ": " + previewTemplate);
				}
			}
			if (!preview.getCar().getEmpty()) {
				count++;
			}
		}
		return count;
	}
	
	public Roxel eventListener(Roxel r) {
		/*
		Roxel leftTemplate = new Roxel();
		leftTemplate.setX((r.getX() + struct.width - 1) % struct.width);
		leftTemplate.setY(r.getY());
		
		Roxel aboveTemplate = new Roxel();
		aboveTemplate.setX(r.getX());
		aboveTemplate.setY((r.getY() + struct.height - 1) % struct.height);
		
		Roxel above = null;
		Roxel left = null;
		
		while (above == null) {
			above = gs.read(aboveTemplate);
			CarRunner.sleep(33);
			if (above == null) {
				System.out.println("Missing roxel, above " + r + ": " + aboveTemplate);
			}
		}
		
		while (left == null) {
			left = gs.read(leftTemplate);
			CarRunner.sleep(33);
			if (left == null) {
				System.out.println("Missing roxel, left of " + r + ": " + leftTemplate);
			}
		}
		
		if (left.car.getEmpty() == false) {
			r.setDrivingDirection(Direction.EAST.value());
		} else if (above.car.getEmpty() == false) {
			r.setDrivingDirection(Direction.SOUTH.value());
		}
		*/
		
		int carsFromNorth = preview(r, true);
		int carsFromWest = preview(r, false);
		
		//System.out.println("Preview for " + r + ": " + carsFromWest + " : " + carsFromNorth);
		
		if (carsFromNorth > carsFromWest) {
			r.setDrivingDirection(Direction.SOUTH.value());
		} else if (carsFromNorth < carsFromWest) {
			r.setDrivingDirection(Direction.EAST.value());
		}
		
		//r.setDrivingDirection(rnd.nextBoolean() ? Direction.SOUTH.value() : Direction.EAST.value());
		System.out.println("Write back: " + r);
		return r;
	}
	
	
	
	@Override
	public void run() {
		Roxel template = new Roxel();
        template.drivingDirection = Direction.TODECIDE.value();
        
		for (;;) {
			Roxel result = null;
			do {
				CarRunner.sleep(3);
				result = gs.take(template);
				if (result == null) {
					CarRunner.sleep(100);
				}
			} while (result == null);
			
			gs.write(eventListener(result));
		}
	}
	
}
