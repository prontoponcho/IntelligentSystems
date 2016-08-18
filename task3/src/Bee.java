import java.util.ArrayList;

import cs480viewer.task3.Viewer;

public class Bee extends MyAgentDetector {
	private String name;
	private Viewer viewer;
	
	public Bee(String name, MyAgentLocation subject, Viewer viewer) {
		super(subject);
		this.name = name;
		this.viewer = viewer;
	}
	
	@Override
	public void advance() {
		if (isTracking()){
			track(targets.get(0));
		} else {
			//do nothing
		}
	}
	
	public void viewDance(BeeScout scout) {
		rotateToScout(scout);
		double x = scout.danceX();
		double z = scout.danceZ();
		makeTarget(x, 0, z);
	}
	
	private void makeTarget(double x, double y, double z) {
		targets.add(new Coordinate(x, y, z));		
	}
	
	private void rotateToScout(BeeScout scout) {
		double targetYaw = (scout.yaw() + 180) % 360;
		while (Math.abs(this.yaw() - targetYaw) > 5) {
			ArrayList<Coordinate> newCourse = Course.rotateByDegrees(this.location(), this.yaw(), 5);
			this.changeCourse(newCourse);
			super.advance();
			updateViewer();
		}		
	}
	
	private void updateViewer() {
		viewer.doAddEvent(name, x(), y(), z(), yaw(), pitch(), roll());
		viewer.doAdvanceEventClock();
	}
	
	@Override
	public String toString() {
		return name;
	}
}
