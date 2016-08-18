import java.util.ArrayList;

public class Course {
	
	private Course() {}
	
	//calculates intermediate coordinates of a minimum distance apart
	public static ArrayList<Coordinate> intermediates(ArrayList<Coordinate> coords, double minDist) {
		ArrayList<Coordinate> temp = new ArrayList<Coordinate>();
		for(int i = 0; i < coords.size() - 1; i++) {
			temp.add(coords.get(i));
			intermediates(coords.get(i), coords.get(i + 1), temp, minDist);
		}
		temp.add(coords.get(coords.size() - 1));
		return temp;
	}
	
	//helper function with in-order traversal
	private static void intermediates(Coordinate c1, Coordinate c2, ArrayList<Coordinate> lst, double minDist) {
		if (Coordinate.distance(c1, c2) < minDist) 
			return;
		Coordinate mid = Coordinate.midPoint(c1, c2);
		intermediates(c1, mid, lst, minDist);
		lst.add(mid);
		intermediates(mid, c2, lst, minDist);
	}
	
	public String toString(ArrayList<Coordinate> coords) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < coords.size() - 1; i++) {
			sb.append(coords.get(i).toString() + ", ");
		}
		sb.append(coords.get(coords.size() - 1) + "]");
		return sb.toString();
	}
	
	public static Coordinate randomCoord() {
		double x = randDouble(-500, 500);
		double y = 0 /*randDouble(0, 500)*/;
		double z = randDouble(-500, 500);
		return new Coordinate(x, y, z);
	}
	
	private static void randomTurn(ArrayList<Coordinate> coords, double currYaw) {
		double newYaw = randDouble(15, 360);
		if (Math.abs(newYaw - currYaw) >= 180)
			turnClockwise(coords, currYaw, newYaw);
		else
			turnCounterClockwise(coords, currYaw, newYaw);
	}
	
	public static void extendByOne(ArrayList<Coordinate> coords, double yaw) {
		double deltaZ = 5 * Math.cos(Math.toRadians(yaw));
		double deltaX = 5 * Math.sin(Math.toRadians(yaw));	
		Coordinate curr = coords.get(coords.size() - 1);
		coords.add(new Coordinate(deltaX + curr.getX(), curr.getY(), deltaZ + curr.getZ()));
	}

	//extends coordinates by length or a random length if null
	public static void extendLength(ArrayList<Coordinate> coords, double length) {
		Coordinate prev = null;
		Coordinate curr = coords.get(coords.size() - 1);
		try {
			prev = coords.get(coords.size() - 2);
		}catch (Exception ex) {
			prev = curr;
		}
		double currYaw = Orientation.yaw(prev, curr);
		if (length < 0) 
			length = randInteger(50, 100);
		for (int i = 0; i < 5*length; i+=5) {
			double deltaZ = 1 * Math.cos(Math.toRadians(currYaw));
			double deltaX = 1 * Math.sin(Math.toRadians(currYaw));
			if (inBounds(curr, deltaX, deltaZ))
				coords.add(new Coordinate(deltaX + curr.getX(), curr.getY(), deltaZ + curr.getZ()));
			else {
				rotate(coords, currYaw, randDouble(30, 180));
				extendLength(coords, -1);
				break;
			}
			curr = coords.get(coords.size() - 1);
		}				
	}
	
	public static ArrayList<Coordinate> redirect(Coordinate startLoc, double yaw) {
		ArrayList<Coordinate> coords = new ArrayList<Coordinate>();
		coords.add(startLoc);
		rotate(coords, yaw, randSign(randDouble(30, 90)));
		extendLength(coords, -1);
		return coords;
	}
	
	public static void rotate(ArrayList<Coordinate> coords, double yaw, double rotation) {
		double newYaw = (yaw + rotation) % 360;
		Coordinate c1 = coords.get(coords.size() - 1);
		double deltaZ = 1 * Math.cos(Math.toRadians(newYaw));
		double deltaX = 1 * Math.sin(Math.toRadians(newYaw));
		coords.add(new Coordinate(deltaX + c1.getX(), c1.getY(), deltaZ + c1.getZ()));
	}
	
	//returns a coordinate of random position from the pass coordinate of the passed distance
	public static Coordinate randRotate(Coordinate coord, double distance) {
		double newYaw = randDouble(0, 360);
		double deltaZ = distance * Math.cos(Math.toRadians(newYaw));
		double deltaX = distance * Math.sin(Math.toRadians(newYaw));
		return new Coordinate(deltaX + coord.getX(), coord.getY(), deltaZ + coord.getZ());
	}
	
	//returns new course that tracks the target
	public static ArrayList<Coordinate> track(Coordinate tracker, Coordinate target, double speed) {
		double deltaX = tracker.getX() - target.getX();
		double deltaZ = tracker.getZ() - target.getZ();
		double yaw = -Math.atan2(deltaZ, deltaX) - Math.PI/2;
		ArrayList<Coordinate> coords = new ArrayList<Coordinate>();
		for (int i = 0; i < 2; i++) {
			deltaX = speed * Math.sin(yaw);
			deltaZ = speed * Math.cos(yaw);
			Coordinate coord = new Coordinate(deltaX + tracker.getX(), tracker.getY(), deltaZ + tracker.getZ());
			coords.add(coord);
			tracker = coords.get(coords.size() - 1);
		}
		return coords;
	}
	
	private static boolean inBounds(Coordinate c, double deltaX, double deltaZ) {
		return Math.abs(c.getX() + deltaX) < 500 && 
			   Math.abs(c.getZ() + deltaZ) < 500;
	}
	
	private static boolean inBounds(Coordinate c) {
		return Math.abs(c.getX()) < 500 && 
			   Math.abs(c.getZ()) < 500;
	}
	
	private static void turnCounterClockwise(ArrayList<Coordinate> coords, double currYaw, double targetYaw) {
		double deltaAngle = 0.8;
		double turnRadius = 0.9;
		while ((currYaw + 360) > targetYaw) {
			currYaw -= deltaAngle;
			double deltaX = turnRadius * Math.sin(Math.toRadians(currYaw));
			double deltaY = 0;
			double deltaZ = turnRadius * Math.cos(Math.toRadians(currYaw));
			Coordinate curr = coords.get(coords.size() - 1);
			if (inBounds(curr, deltaX, deltaZ)) {
				coords.add(new Coordinate(deltaX + curr.getX(), deltaY + curr.getY(), deltaZ + curr.getZ()));
			} else {
				rotate(coords, currYaw, 180);
				extendLength(coords, -1);
				break;
			}
		}
	}
	
	private static void turnClockwise(ArrayList<Coordinate> coords, double currYaw, double targetYaw) {
		double deltaAngle = 1;
		double turnRadius = 0.75;
		while (Math.abs(currYaw) < Math.abs(targetYaw)) {
			currYaw += deltaAngle;
			double deltaX = turnRadius * Math.sin(Math.toRadians(currYaw));
			double deltaY = 0;
			double deltaZ = turnRadius * Math.cos(Math.toRadians(currYaw));
			Coordinate curr = coords.get(coords.size() - 1);
			if (inBounds(curr, deltaX, deltaZ)) {
				coords.add(new Coordinate(deltaX + curr.getX(), deltaY + curr.getY(), deltaZ + curr.getZ()));
			} else {
				rotate(coords, currYaw, 180);
				extendLength(coords, -1);
				break;
			}
		}
	}
	
	//create new course segment from the specified start coordinate and orientation
	public static ArrayList<Coordinate> newSegment(Coordinate start, double yaw, double length) {
		ArrayList<Coordinate> coords = new ArrayList<Coordinate>();
		if (start == null) {
			start = randomCoord();
			coords.add(start);
			Coordinate end = randRotate(start, length);
			while (!inBounds(end))
				end = randRotate(start, length);
			coords.add(end);
			coords = intermediates(coords, 1);
		} else {
			coords.add(start);
			randomTurn(coords, yaw);
			extendLength(coords, length);
		}
		return coords;
	}
   
	private static double randDouble(double min, double max) {
	    return Math.random() * (max - min + 1) + min;
	}
	
	private static int randInteger(int min, int max) {
	    return (int)(Math.random() * (max - min + 1) + min);
	}
	
	private static double randSign(double d) {
		double sign = Math.random();
		return sign < 0.5 ? -d : d;
	}
	
    
	//for testing
	public static void main(String[] args) {

	}
}
