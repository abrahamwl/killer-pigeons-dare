package net.bithaven.efficiencyrpg.levelgenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import net.bithaven.efficiencyrpg.Dir;
import net.bithaven.efficiencyrpg.Room;
import net.bithaven.efficiencyrpg.entity.Character;
import net.bithaven.efficiencyrpg.levelgenerator.Util.PathMetric;

public class PathFinder {
	// Default to simple distance metric
	PathFinder(){}	
	PathMetric pm = new PathMetric() {			
		@Override
		public double measure(int sx, int sy, int ex, int ey, Character c, Room r) {
			return Math.hypot(sx - ex,  sy - ey);
		}
	};
	
	// Allow custom metrics
	PathFinder(PathMetric pm) {
		this.pm = pm;
	}

	public Coord pathCheck(int sx, int sy, int ex, int ey, Room room,
			Character hero, int limit) {
		// Search uses a PriorityQueue to 
		// make it a rudimentary A* search
		Queue<Coord> open = new PriorityQueue<Coord>();
		List<Coord> closed = new ArrayList<Coord>();
		
		Coord start = new Coord(sx, sy, null, Double.MAX_VALUE);
		Coord end = new Coord(ex, ey, null, Double.MIN_VALUE);
		open.add(start);
		closed.add(start);
		
		// Starting with an initial position, it adds 
		// all passable neighboring positions that 
		// haven't been added yet.  Each position gets 
		// a heuristic value marking its distance from 
		// the destination.  The priority queue will 
		// always remove the next closest point.
		while(open.size() > 0 && limit-- > 0) {
			Coord currPos = open.remove();
			for(Dir d : Dir.values()) {
				if(d == Dir.NO_DIRECTION) continue;
				
				int cx = currPos.x + d.x;
				int cy = currPos.y + d.y;
				if(cx < 0 || cx == room.width || cy < 0 || cy == room.height) continue;

				Coord newCoord = new Coord(cx, cy, currPos, pm.measure(cx, cy, ex, ey));
				if(closed.contains(newCoord)) continue;
				if(!room.checkForPassableAt(newCoord.x, newCoord.y, hero)) continue;

				if(newCoord.equals(end)) return newCoord;

				open.add(newCoord);
				closed.add(newCoord);
			}	
		}
		
		// If all passable positions have been accessed 
		// without reaching the destination, or the search 
		// depth has been exceeded, mark the destination 
		// unreachable.
		return null;
	}
}
