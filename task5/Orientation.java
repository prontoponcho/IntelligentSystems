
public class Orientation {

	public static double yaw(Coordinate c1, Coordinate c2) {
		double deltaX = c2.getX() - c1.getX();
		double deltaZ = c2.getZ() - c1.getZ();
		double angle = Math.atan2(deltaX, deltaZ);
		angle = Math.toDegrees(angle);
		return angle < 0 ? angle + 360 : angle;
	}

}

