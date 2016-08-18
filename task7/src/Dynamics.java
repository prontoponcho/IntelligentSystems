
public class Dynamics {
	
	//returns the lift coefficient as an average of roll and speed contributions
	private static double liftFactor(double knots, double roll, double weight) {
		double rollFactor = Math.cos(Math.toRadians(roll));
		double speedFactor = 0.008325 * knots + 0.001;
		return ((speedFactor + rollFactor) / 2.0) * (1 / (weight / 1000.0));
	}

	//returns new y coordinate
	private static double deltaY(double knots, double roll, double weight) {
		double lift = liftFactor(knots, roll, weight);
		double deltaY = 0;
		if (lift < 1.0) {
			deltaY -= 1.0 - lift;
		} else if (lift > 1.0){
			deltaY += lift - 1.0;
		}
		return deltaY;
	}
	
	private static double deltaX(double speed, double roll, double yaw) {
		double nextYaw = (yaw + Math.sin(Math.toRadians(roll))) % 360.0;
		return speed * Math.sin(Math.toRadians(nextYaw));
	}
	
	private static double deltaZ(double speed, double roll, double yaw) {
		double nextYaw = (yaw + Math.sin(Math.toRadians(roll))) % 360.0;
		return speed * Math.cos(Math.toRadians(nextYaw));
	}
	
	public Coordinate nextCoord(Coordinate curr, double knots, double roll, double yaw, double weight, double reductionFactor) {
		double speed = (knots * 0.514) / reductionFactor; //converting to m/s & reducing by a factor
		double nextX = curr.x() + deltaX(speed, roll, yaw);
		double nextZ = curr.z() + deltaZ(speed, roll, yaw);
		double nextY = curr.y() + deltaY(knots, roll, weight);
		return new Coordinate(nextX, nextY, nextZ);
	}

}
