import java.util.ArrayList;
import cs480viewer.task3.Viewer;

public class MyAgent {
	protected MyAgentLocation subject = null;
	protected ArrayList<Coordinate> course = new ArrayList<Coordinate>();
	protected int curr = 0; //current coordinate
	private double roll = 0;
	
	public MyAgent() {//this xtor does not register with subject
		course = Course.newSegment(null, 0);
	}
	
	public MyAgent(ArrayList<Coordinate> course) {
		this.course = course;
	}
	
	public MyAgent(MyAgentLocation subject) {
		this.subject = subject;
		subject.registerAgent(this);
		course = Course.newSegment(null, 0);
	}
	
	public MyAgent(MyAgentLocation subject, ArrayList<Coordinate> course) {
		this.subject = subject;
		subject.registerAgent(this);
		this.course = course;
	}

	public double x() {return course.get(curr).getX();}
	public double y() {return course.get(curr).getY();}
	public double z() {return course.get(curr).getZ();}
	public double yaw() {return Orientation.yaw(course.get(curr), course.get(curr + 1));}
	public double pitch() { return Orientation.pitch(course.get(curr), course.get(curr + 1));}
	public double roll() { return roll;}
	
	//used exclusively for the scout's dance
	public Coordinate location() {return course.get(curr);}
	public void setRoll(double d) {roll = d;}
	
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
		course = Course.newSegment(this.location(), this.yaw());
		curr = 0;
	}
	
	protected void changeCourse(ArrayList<Coordinate> course) {
		this.course = course;
		curr = 0;
	}
	
	//testing
	public static void main(String[] args) {

	}
}
