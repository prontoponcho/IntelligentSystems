import java.util.ArrayList;

import cs480viewer.task5.Viewer;

public class Pulse extends MyAgent {
	private String name = "pulse";
	private boolean detectedBug = false;
//	private MyAgentLocation myAgentLocation = null;
	
	public String name() {return name;}
	
	public void setDetectedBug(boolean b) {detectedBug = b;}
	
	public Pulse (Coordinate start, double yaw) {
		course = new ArrayList<Coordinate>();
		course.add(start);
		Course.extendByOne(course, yaw);
		Course.extendLength(course, 250);
	}
	
//	public Pulse (Coordinate start, double yaw, MyAgentLocation mal) {
//		course = new ArrayList<Coordinate>();
//		course.add(start);
//		Course.extendByOne(course, yaw);
//		Course.extendLength(course, 250);
//		myAgentLocation = mal;
//	}
	
	@Override
	public void advance() {
		if (endOfCourse()) { 
			//do nothing;
		} else if (detectedBug) {
			curr = course.size() - 1;
		} else {
			curr++; 
		}
	}
	
//	public Bug detect() {
//		return myAgentLocation.detect(this);
//	}
	
	
	public static void main(String[] args) {
//		Viewer _viewer = new Viewer("C:\\Users\\Richard\\Google Drive\\EWU\\CSCD480 Intelligent Systems\\workspace\\task5\\tracks\\Pulse_test.trk", 0);
//		
//		//instantiate bug in fixed location
//		ArrayList<Coordinate> location = new ArrayList<Coordinate>();
//		location.add(new Coordinate(200, 0, 0));
//		location.add(new Coordinate (201, 0, 0));
//		Bug bug = new Bug("bug1",location);
//		
//		//instantiate MyAgentLocation and register bug
//		MyAgentLocation mal = new MyAgentLocation();
//		mal.registerAgent(bug);
//		
//		//instantiate pulse at center
//		Pulse pulse = new Pulse(new Coordinate(0,0,0), 90);
//		
//		while (true) {
//
//			_viewer.doAddEvent(bug.name(), bug.x(), bug.y(), bug.z(), bug.yaw(), 0, 0);
//			bug.advance();
//			
//			_viewer.doAddEvent(pulse.name(), pulse.x(), pulse.y(), pulse.z(), pulse.yaw(), 0, 0);
//			pulse.advance();
//			
//			Bug detectedBug = pulse.detect();
//			if (detectedBug != null) {
//				_viewer.doHighlightAgent(detectedBug.name());
//			}
//			
//			_viewer.doAdvanceEventClock();
		}
}



