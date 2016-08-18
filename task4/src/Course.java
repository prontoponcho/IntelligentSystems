import java.util.ArrayList;

public class Course {
	
	private Course() {}
	
	public static ArrayList<Coordinate> square() {
		Coordinate c1 = new Coordinate(200, 0, 200);
		Coordinate c2 = new Coordinate(200, 0, -200);
		Coordinate c3 = new Coordinate(-200, 0, -200);
		Coordinate c4 = new Coordinate(-200, 0, 200);
		ArrayList<Coordinate> sideOne = new ArrayList<Coordinate>();
		sideOne.add(c1); sideOne.add(c2);
		sideOne = intermediates(sideOne, 5);
		ArrayList<Coordinate> sideTwo = new ArrayList<Coordinate>();
		sideTwo.add(c2); sideTwo.add(c3);
		sideTwo = intermediates(sideTwo, 5);
		ArrayList<Coordinate> sideThree = new ArrayList<Coordinate>();
		sideThree.add(c3); sideThree.add(c4);
		sideThree = intermediates(sideThree, 5);
		ArrayList<Coordinate> sideFour = new ArrayList<Coordinate>();
		sideFour.add(c4); sideFour.add(c1);
		sideFour = intermediates(sideFour, 5);
		ArrayList<Coordinate> course = new ArrayList<Coordinate>();
		course.addAll(sideOne);
		course.addAll(sideTwo);
		course.addAll(sideThree);
		course.addAll(sideFour);
		return course;
	}
	
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
	
	//extends coordinates by length or a random length if null
	public static void extendLength(ArrayList<Coordinate> coords, double length) {
		Coordinate prev = coords.get(coords.size() - 2);
		Coordinate curr = coords.get(coords.size() - 1);
		double currYaw = Orientation.yaw(prev, curr);
		if (length < 0) 
			length = randInteger(50, 100);
		for (int i = 0; i < length; i++) {
			double deltaZ = 1 * Math.cos(Math.toRadians(currYaw));
			double deltaX = 1 * Math.sin(Math.toRadians(currYaw));
			coords.add(new Coordinate(deltaX + curr.getX(), curr.getY(), deltaZ + curr.getZ()));
			curr = coords.get(coords.size() - 1);
		}				
	}
	
	private static int randInteger(int min, int max) {
	    return (int)(Math.random() * (max - min + 1) + min);
	}
	
	private static double randDouble(double min, double max) {
	    return Math.random() * (max - min + 1) + min;
	}
	
	//for testing
	public static void main(String[] args) {

	}
}
