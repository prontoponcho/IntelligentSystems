
public 	class Coordinate {
	private double x;
	private double y;
	private double z;
	
	public Coordinate(double x, double y, double z) {
		if (!inBounds(x) || !inBounds(y) || !inBounds(z))
			throw new IllegalArgumentException("passed data out of bounds");
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	private boolean inBounds(double d) {
		return -500 <= d && d <= 500;
	}
	
	public double x() {return x;}
	public double y() {return y;}
	public double z() {return z;}

	
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
	
	public double yaw(Coordinate that) {
		if (that == null) return 0;
		double deltaX = that.x() - this.x();
		double deltaZ = that.z() - this.z();
		double angle = Math.atan2(deltaX, deltaZ);
		return Math.toDegrees(angle);
	}
	
	public double pitch(Coordinate that) {
		if (that == null) return 0;
		double deltaY = that.y() - this.y();
		double hyp = this.distance(that);
		double angle = Math.asin(deltaY / hyp);
		return Math.toDegrees(angle);
	}
	
	public double distance(Coordinate that) {
		double deltaX = this.x() - that.x();
		//double deltaY = this.y() - that.y();
		double deltaZ = this.z() - that.z();
		double sumOfSquares = deltaX * deltaX /*+ deltaY * deltaY*/ + deltaZ * deltaZ;
		return Math.sqrt(sumOfSquares);
	}
	
	public static Coordinate randomCoord() {
		double x = randDouble(-500, 500);
		double y = 0/*randDouble(-500, 500)*/;
		double z = randDouble(-500, 500);
		return new Coordinate(x, y, z);
	}
	
	private static double randDouble(double max, double min) {
		return Math.random() * (max - min) + min;
	}
}
