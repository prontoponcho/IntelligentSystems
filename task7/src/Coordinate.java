
public class Coordinate {
	private double x;
	private double y;
	private double z;
	
	public Coordinate(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double x() {return x;}
	public double y() {return y;}
	public double z() {return z;}
	
	public boolean inBounds(double d) {
		return -500 <= d && d <= 500;
	}
	
	public static double distance(Coordinate c1, Coordinate c2) {
		if (nullCheck(c1)) throw new IllegalArgumentException("c1 can't be null");
		if (nullCheck(c2)) throw new IllegalArgumentException("c2 can't be null");
		double deltaX = c2.x() - c1.x();
		double deltaY = c2.y() - c1.y();
		double deltaZ = c2.z() - c1.z();
		double sumOfSquares = deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
		return Math.sqrt(sumOfSquares);
	}
	
	public static double yaw(Coordinate c1, Coordinate c2) {
		if (nullCheck(c1)) throw new IllegalArgumentException("c1 can't be null");
		if (nullCheck(c2)) throw new IllegalArgumentException("c2 can't be null");
		double deltaX = c2.x() - c1.x();
		double deltaZ = c2.z() - c1.z();
		double angle = Math.atan2(deltaX, deltaZ);
		return Math.toDegrees(angle);
	}
	
	public static double pitch(Coordinate c1, Coordinate c2) {
		if (nullCheck(c1)) throw new IllegalArgumentException("c1 can't be null");
		if (nullCheck(c2)) throw new IllegalArgumentException("c2 can't be null");
		double deltaY = c2.y() - c1.y();
		double hyp = distance(c1, c2);
		double angle = Math.asin(deltaY / hyp);
		return Math.toDegrees(angle);
	}	
	
	private static boolean nullCheck(Object o) {
		return o == null;
	}
	
	public static Coordinate randomCoord() {
		double x = randDouble(-500, 500);
		double y = randDouble(-500, 500);
		double z = randDouble(-500, 500);
		return new Coordinate(x, y, z);
	}
	
	private static double randDouble(double max, double min) {
		return Math.random() * (max - min) + min;
	}
	
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
	
	//testing
	public static void main(String[] args) {
	}

}
