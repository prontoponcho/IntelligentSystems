import java.util.ArrayList;
import cs480viewer.task5.Viewer;

public class Bug extends MyAgent {
	private String name;
	private boolean detected = false;
	private boolean eaten = false;
	
	public Bug(String name) {
		this.name = name;
		this.course = Course.newSegment(null, 0, 5);
	}
	
	public Bug(String name, ArrayList<Coordinate> c) {
		this.name = name;
		this.course = c;
	}

	public String name() {return name;}
	
	public boolean isDetected() {return detected;}
	public boolean isEaten() {return eaten;}
	public void setDetected(boolean bool) {detected = bool;}
	public void setEaten(boolean bool) {eaten = bool;}

	@Override
	protected void changeCourse() {
		course = Course.newSegment(this.location(), this.yaw(), 20);
		curr = 0;
	}
	

	public static void main(String[] args) {
		Viewer _viewer = new Viewer("C:\\Users\\Richard\\Google Drive\\EWU\\CSCD480 Intelligent Systems\\workspace\\task5\\tracks\\track5_1_2.trk", 0);
		Bug[] bugs = new Bug[4];
		for (int i = 0; i < bugs.length; i++) {
			bugs[i] = new Bug("bug" + (i + 1));
		}

		while (true) {
			for (int i = 0; i < bugs.length; i++) {
				_viewer.doAddEvent(bugs[i].name(), bugs[i].x(), bugs[i].y(), bugs[i].z(), bugs[i].yaw(), 0, 0);
				bugs[i].advance();
			}

			_viewer.doAdvanceEventClock();

		}
	}
}

