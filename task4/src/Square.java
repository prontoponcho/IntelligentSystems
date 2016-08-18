import java.util.ArrayList;

public class Square {
	private ArrayList<Coordinate> course = new ArrayList<Coordinate>();
	
	public Square() {
		course = Course.square();
	}
	
	private int closestCoordinateIndex(Coordinate coord) {
		double minDistance = Double.POSITIVE_INFINITY;
		int closest = -1;
		for (int i = 0; i < course.size(); i++) {
			double distance = Coordinate.distance(course.get(i), coord);
			if (distance < minDistance) {
				minDistance = distance;
				closest = i;
			}
		}
		return closest;
	}
	
	//return coordinate 5 indexes ahead of the coordinate closest to parameter.
	public Coordinate getTargetCoordinate(Coordinate coord) {
		int closest = closestCoordinateIndex(coord);
		closest = (closest + 10) % course.size();
		return course.get(closest);
	}

}
