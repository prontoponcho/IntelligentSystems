import java.util.ArrayList;
import java.util.List;

public class Orientation {

	public static double pitch(Coordinate c1, Coordinate c2) {
		double deltaY = c2.getY() - c1.getY();
		double hyp = Coordinate.distance(c1, c2);
		double angle = Math.asin(deltaY / hyp);
		return Math.toDegrees(angle);
	}
	
	public static double yaw(Coordinate c1, Coordinate c2) {
		double deltaX = c2.getX() - c1.getX();
		double deltaZ = c2.getZ() - c1.getZ();
		double angle = Math.atan2(deltaX, deltaZ);
		return Math.toDegrees(angle);
	}
	
	public static ArrayList<Double> getPitches(List<Coordinate> coords) {
		ArrayList<Double> pitches = new ArrayList<Double>();
		for (int i = 0; i < coords.size() - 1; i++) {
			double pitch = pitch(coords.get(i), coords.get(i + 1));
			pitches.add(pitch);
		}
		return pitches;
	}
	
	public static ArrayList<Double> getYaws(List<Coordinate> coords) {
		ArrayList<Double> yaws = new ArrayList<Double>();
		for (int i = 0; i < coords.size() - 1; i++) {
			double yaw = yaw(coords.get(i), coords.get(i + 1));
			yaws.add(yaw);
		}
		return yaws;
	}

}
