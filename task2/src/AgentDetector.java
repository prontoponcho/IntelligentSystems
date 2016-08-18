import java.util.ArrayList;

import cs480viewer.task2.Viewer;

public class AgentDetector extends Agent{
	private AgentLocation subject = null;
	private Agent target = null;
	private double viewingAngle = 40;
	private double viewingDepth = 110;
	
	public AgentDetector(AgentLocation subject) {
		super();
		this.subject = subject; //does not register with subject
	}
	
	@Override
	public void advance() {
		if (isTracking()) {
			track();
		} else {
			target = subject.detect(this);
			super.advance();
		}
	}
	
	private boolean isTracking() {
		return target != null;
	}
	
	private void track() {
		if (isDistantTarget()) {
			course = Course.track(this.location(), target.location(), 2.3);
			curr = 0;
		} else {
			target = null;
			super.changeCourse();
			for (int i = 0; i < 5; i++)
				super.advance();
		}
	}
	
	private boolean isDistantTarget() {
		return Coordinate.distance(target.location(), this.location()) > 20;
	}
	
	public boolean inView(Agent a) {
		if (a.y() < this.y() - 10) return false;
		Coordinate pt = a.location();
		Coordinate v1 = this.rightViewPoint();
		Coordinate v2 = this.leftViewPoint();
		Coordinate v3 = this.location();
		return Coordinate.pointInTriangle(pt, v1, v2, v3);
	}
	
	private Coordinate leftViewPoint() {
		return viewPoint(-viewingAngle, viewingDepth);
	}
	
	private Coordinate rightViewPoint() {
		return viewPoint(viewingAngle, viewingDepth);
	}
	
	private Coordinate viewPoint(double angle, double length) {
		double relativeAngle = (this.yaw() + angle) % 360;
		double deltaX = length * Math.sin(Math.toRadians(relativeAngle));
		double deltaY = 0;
		double deltaZ = length * Math.cos(Math.toRadians(relativeAngle));
		return new Coordinate(deltaX + this.x(), deltaY + this.y(), deltaZ + this.z());
	}
	
	//testing
	public static void main(String[] args) {
//		Viewer _viewer = new Viewer("C:\\Users\\Richard\\Google Drive\\EWU\\CSCD480 Intelligent Systems\\workspace\\task2\\tracks\\task2_detector.trk", 5);
//		AgentLocation subject = new AgentLocation();
//		Agent[] fish = new Agent[5];
//		for (int i = 0; i < fish.length; i++) {
//			fish[i] = new AgentObserver(subject);
//
//		}
//		Agent dolphin = new AgentDetector(subject);
// 
//		while(true) {
//			for (int j = 0; j < fish.length; j++) {
//				String fishy = "fish" + (j + 1);
//				_viewer.doAddEvent(fishy, fish[j].x(), fish[j].y(), fish[j].z(), fish[j].yaw(), 0, 0);
//				fish[j].advance();
//			}
//			_viewer.doAddEvent("dolphin", dolphin.x(), dolphin.y(), dolphin.z(), dolphin.yaw(), 0, 0);
//			dolphin.advance();
//			_viewer.doAdvanceEventClock(); 
//		}
	}

}
