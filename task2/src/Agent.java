import java.util.ArrayList;

import cs480viewer.task2.Viewer;

public class Agent {
	protected ArrayList<Coordinate> course = new ArrayList<Coordinate>();
	protected int curr = 0; //current coordinate
	
	public Agent() {
		course = Course.newSegment(null, 0);
	}
	
	public Agent(ArrayList<Coordinate> c) {
		course = c;
	}

	public double x() {return course.get(curr).getX();}
	public double y() {return course.get(curr).getY();}
	public double z() {return course.get(curr).getZ();}
	public double yaw() {return Orientation.yaw(course.get(curr), course.get(curr + 1));}
	public double pitch() { return Orientation.pitch(course.get(curr), course.get(curr + 1));}
	public Coordinate location() {return course.get(curr);}
	
	public void advance() {
		if (endOfCourse()) { 
			changeCourse();
		} else {
			curr++;
		}
	}
	
	private boolean endOfCourse() {
		return curr + 2 >= course.size();
	}
	
	protected void changeCourse() {
		course = Course.newSegment(this.location(), this.yaw());
		curr = 0;
	}
	
	public static void main(String[] args) {
		Agent[] agents = new Agent[1];
		for (int i = 0; i < agents.length; i++)
			agents[i] = new Agent(Course.square());
		Viewer _viewer = new Viewer("C:\\Users\\Richard\\Google Drive\\EWU\\CSCD480 Intelligent Systems\\workspace\\task2\\tracks\\square_course_test.trk", 100);
		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < agents.length; j++) {
				String fish = "fish" + (j + 1);
				_viewer.doAddEvent(fish, agents[j].x(), agents[j].y(), agents[j].z(), agents[j].yaw(), agents[j].pitch(), 0);			
				agents[j].advance();
			}
			_viewer.doAdvanceEventClock(); 
		}
	}
}