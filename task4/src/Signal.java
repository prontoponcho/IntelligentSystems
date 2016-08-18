import java.util.ArrayList;

public class Signal{
	private ArrayList<Coordinate> course = new ArrayList<Coordinate>();
	private int curr = 0; //current coordinate
	
	//getters
	public double x() {return course.get(curr).getX();}
	public double y() {return course.get(curr).getY();}
	public double z() {return course.get(curr).getZ();}
	public double yaw() {return Orientation.yaw(course.get(curr), course.get(curr + 1));}
	public double pitch() { return Orientation.pitch(course.get(curr), course.get(curr + 1));}
	public Coordinate location() {return course.get(curr);}
	
	public Signal(Coordinate start, Coordinate target) {
		course.add(start);
		course.add(target);
		course = Course.intermediates(course, 5);
	}

	public void advance() {
		if (endOfCourse()) {
			//do nothing
		} else {
			curr++;
		}
	}
	
	private boolean endOfCourse() {
		return curr + 2 >= course.size();
	}
	
	public double getXWithError() {
		double count = Coordinate.distance(course.get(0), course.get(course.size() - 1));
		count /= 5; //number of 5 meter propagations
		double error = 0;
		for (int i = 0; i < count; i++)
			error += randDouble(-0.6, 0.7);
		return course.get(course.size() - 1).getX() + error;
	}
	
	public double getZWithError() {
		double count = Coordinate.distance(course.get(0), course.get(course.size() - 1));
		count /= 5; //number of 5 meter propagations
		double error = 0;
		for (int i = 0; i < count; i++)
			error += randDouble(-0.6, 0.7);
		return course.get(course.size() - 1).getZ() + error;
	}
	
	private static double randDouble(double min, double max) {
	    return Math.random() * (max - min) + min;
	}
	

}
