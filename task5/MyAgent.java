import java.util.ArrayList;

public class MyAgent {

	protected ArrayList<Coordinate> course = new ArrayList<Coordinate>();
	protected int curr = 0; //current coordinate

	public MyAgent() {
		course = Course.newSegment(null, 0, 300);
	}

	public MyAgent(ArrayList<Coordinate> c) {
		course = c;
	}

	public double x() {return course.get(curr).getX();}
	public double y() {return course.get(curr).getY();}
	public double z() {return course.get(curr).getZ();}
	public double yaw() {return Orientation.yaw(course.get(curr), course.get(curr + 1));}
	public Coordinate location() {return course.get(curr);}

	public void advance() {
		if (endOfCourse()) { 
			changeCourse();
		} else {
			curr++;
		}
	}

	protected boolean endOfCourse() {
		return curr + 2 >= course.size();
	}

	protected void changeCourse() {
		course = Course.newSegment(this.location(), this.yaw(), 300);
		curr = 0;
	}
	
	public String toString() {
		return "location (" + x() + ", " + y() + ", " + z() + ")";
	}

	public static void main(String[] args) {

	}
}

