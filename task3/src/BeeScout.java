import java.util.ArrayList;

import cs480viewer.task3.Viewer;

public class BeeScout extends MyAgentDetector {
	private String name = null;
	private Flower flower = null;
	private Viewer viewer = null;
	
	public BeeScout(String name, MyAgentLocation subject, Viewer viewer) {
		super(subject);
		this.name = name;
		this.viewer = viewer;
	}
	
	@Override 
	public void advance() {
		if (isScouting()) {
			scout();
		} else if (isTracking()){
			track(targets.get(0));
		} else {
			//do nothing
		}
	}
	
	private boolean isScouting() {
		return flower == null;
	}
	
	private void scout() {
		MyAgent agent = subject.detect(this);
		if (agent instanceof Flower) {
			flower = (Flower) agent;
			getBeeTargets();
			advance();
		} else super.advance();
	}
	
	private void getBeeTargets() {
		targets = subject.getRegisterantLocations();
	}
	
	@Override
	protected void track(Coordinate target) {
		if (isDistantTarget(target)) {
			ArrayList<Coordinate> newCourse = Course.track(this.location(), target, 1);
			changeCourse(newCourse);
		} else if (getTarget() instanceof Bee){ //reached a bee
			Bee bee = (Bee) getTarget();
			bee.viewDance(this);
			targets.remove(0);
		} else { //returned to flower
			targets.remove(0);
		}
	}
	
	private MyAgent getTarget() {
		return subject.getRegistrant(targets.get(0));
	}
	
	public double danceX() {
		double x = 0;
		for (int i = 0; i < Math.abs(flower.x()); i++) {
			this.swivel(10);
			x++;
		}
		return Math.signum(flower.x())*x;
	}
	
	public double danceZ() {
		double z = 0;
		for (int i = 0; i < Math.abs(flower.z()); i++) {
			this.swivel(20);
			z++;
		}
		return Math.signum(flower.z()) * z;
	}
	
	
	
	private void swivel(int degrees) {
		double targetYaw = (this.yaw() + degrees) % 360;
		while(Math.abs(this.yaw() - targetYaw) > 5) {
			ArrayList<Coordinate> newCourse = Course.rotateByDegrees(this.location(), this.yaw(), 5);
			this.changeCourse(newCourse);
			aadvance();
			updateViewer();
		}		
	}
	
	private void updateViewer() {
		viewer.doAddEvent(name, x(), y(), z(), yaw(), pitch(), roll());
		viewer.doAdvanceEventClock();
	}
	
	public void aadvance() {
		if (endOfCourse()) { 
			changeCourse();
		} else {
			curr++;
		}
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	//testing
	public static void main(String[] args) {
		Viewer _viewer = new Viewer("C:\\Users\\Richard\\Google Drive\\EWU\\CSCD480 Intelligent Systems\\workspace\\task3\\tracks\\task3_test.trk", 1);
		MyAgentLocation subject = new MyAgentLocation(_viewer);
		
		//BeeScout
		ArrayList<Coordinate> course1 = new ArrayList<Coordinate>();
		course1.add(new Coordinate(50,0,0));
		course1.add(new Coordinate(-50,0,0));
		course1 = Course.intermediates(course1, 3);
		BeeScout scout = new BeeScout("scout", subject, _viewer);
		scout.changeCourse(course1);
		
		Bee bee1 = new Bee("bee1", subject, _viewer);
		Bee bee2 = new Bee("bee2", subject, _viewer);
		Bee bee3 = new Bee("bee3", subject, _viewer);
		Bee bee4 = new Bee("bee4", subject, _viewer);
		
		//Flower
		ArrayList<Coordinate> course2 = new ArrayList<Coordinate>();
		course2.add(new Coordinate(-100,0,10));
		course2.add(new Coordinate(50,0,0));
		course2 = Course.intermediates(course2, 3);
		Flower flower = new Flower(course2);
		subject.registerAgent(flower);

		
		while (true) {
			_viewer.doAddEvent("scout", scout.x(), scout.y(), scout.z(), scout.yaw(), scout.pitch(), 0);
			_viewer.doAddEvent("bee1", bee1.x(), bee1.y(), bee1.z(), bee1.yaw(), bee1.pitch(), 0);
			_viewer.doAddEvent("bee2", bee2.x(), bee2.y(), bee2.z(), bee2.yaw(), bee2.pitch(), 0);
			_viewer.doAddEvent("bee3", bee3.x(), bee3.y(), bee3.z(), bee3.yaw(), bee3.pitch(), 0);
			_viewer.doAddEvent("bee4", bee4.x(), bee4.y(), bee4.z(), bee4.yaw(), bee4.pitch(), 0);
			_viewer.doAddEvent("flower", flower.x(), flower.y(), flower.z(), flower.yaw(), flower.pitch(), 0);
			_viewer.doAdvanceEventClock(); 
			bee1.advance();
			bee2.advance();
			bee3.advance();
			bee4.advance();
			scout.advance();
			flower.advance();
		}
		
	}
}
