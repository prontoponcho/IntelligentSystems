import java.util.ArrayList;

//any object that needs to use vision
public class MyAgentDetector extends MyAgentObserver {
	protected ArrayList<Coordinate> targets = new ArrayList<Coordinate>();
	protected double viewingAngle = 30;
	protected double viewingDepth = 50;
	
	public MyAgentDetector(MyAgentLocation subject) {
		super(subject);
	}
	
	@Override
	public void advance() {
		if (isTracking()) {
			track(targets.get(0));
		} else {
			super.advance();
		}
	}
	
	protected boolean isTracking() {
		return targets.size() > 0;
	}
	
	protected void track(Coordinate target) {
		if (isDistantTarget(target)) {
			ArrayList<Coordinate> newCourse = Course.track(this.location(), target, 1);
			changeCourse(newCourse);
		} else { 
			targets.remove(0);
		}
	}
	
	public boolean isDistantTarget(Coordinate target) {
		return Coordinate.distance(this.location(), target) > viewingDepth;
	}
	
	public boolean inView(Coordinate coord) {
		Coordinate v1 = this.rightViewPoint();
		Coordinate v2 = this.leftViewPoint();
		Coordinate v3 = this.location();
		return Coordinate.pointInTriangle(coord, v1, v2, v3);
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

	}
}
