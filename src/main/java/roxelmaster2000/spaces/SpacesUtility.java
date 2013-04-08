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

import roxelmaster2000.Direction;
import roxelmaster2000.pojos.Roxel;
import roxelmaster2000.pojos.Structure;

public class SpacesUtility {
	public static GigaSpace getGigaspace() {
		return DataGridConnectionUtility.getSpace("roxelmaster2000");
	}
	
	static public void initGameField(GigaSpace gs, Structure structure) {
		gs.write(structure);
		
		final int H_ROADS = 10;
		final int V_ROADS = 10;
		
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
        		if (horizontals.contains(y)) {
        			direction |= Direction.EAST.value();
        		}
        		if (verticals.contains(x)) {
        			direction |= Direction.SOUTH.value();
        		}
        		
        		if (direction != 0) {
        			Roxel r = new Roxel();
        			r.direction = direction;
        			r.x = x;
        			r.y = y;
        			gs.write(r);
        		}
        	}
        }

	}
}
