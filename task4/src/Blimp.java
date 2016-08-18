import java.util.ArrayList;

import cs480viewer.task5.Viewer;

public class Blimp {
	private ArrayList<Coordinate> course = new ArrayList<Coordinate>();
	private ArrayList<Coordinate> actualPosition = new ArrayList<Coordinate>();
	private final Square SQUARE = new Square();
	private int curr = 0; //current coordinate
	private Station statOne = null;
	private Station statTwo = null;
	private Signal sigOne = null;
	private Signal sigTwo = null;
	private Coordinate targetPosition = null;
	private double viewingDepth = 5;
	
	public double actualX() {return actualPosition.get(curr).getX();}
	public double actualY() {return actualPosition.get(curr).getY();}
	public double actualZ() {return actualPosition.get(curr).getZ();}
	public double actualYaw() {return Orientation.yaw(actualPosition.get(curr), actualPosition.get(curr + 1));}
	public double actualPitch() { return Orientation.pitch(actualPosition.get(curr), actualPosition.get(curr + 1));}
	public Coordinate actualLocation() {return actualPosition.get(curr);}
	
	public double x() {return course.get(curr).getX();}
	public double y() {return course.get(curr).getY();}
	public double z() {return course.get(curr).getZ();}
	public double yaw() {return Orientation.yaw(course.get(curr), course.get(curr + 1));}
	public double pitch() { return Orientation.pitch(course.get(curr), course.get(curr + 1));}
	public Coordinate location() {return course.get(curr);}
	
	public Blimp(Coordinate start, Station one, Station two) {
		actualPosition.add(start);
		actualPosition.add(start);
		course.add(start);
		course.add(start);
		statOne = one;
		statTwo = two;
	}
	
	public void advance() {
		if (isDetecting())
			detect();
		else if (isTracking())
			track();
		else
			generateSignals();
	}
	
	//target location
	private boolean isTracking() {
		return targetPosition != null;
	}
	
	private boolean isDetecting() {
		return sigOne == null || sigTwo == null;
	}
	
	//
	private void track() {
		if (isDistantTarget()) {
			course = Course.track(this.location(), targetPosition, 2);
			actualPosition = Course.track(actualLocation(), targetPosition, 2);
			curr = 0;
		} else {
			targetPosition = null;
		}
	}
	
	private boolean isDistantTarget() {
		return Coordinate.distance(targetPosition, this.location()) >= 1;
	}
	
	private void detect() {
		if (sigOne == null)
			sigOne = statOne.signal();
		if (isDetected(sigOne)) {
			statOne.stopSignal();
		} else {
			sigOne = null;
			statOne.advanceSignal();
		}
		
		if (sigTwo == null)
			sigTwo = statTwo.signal();
		if (isDetected(sigTwo)) {
			statTwo.stopSignal();
		} else {
			sigTwo = null;
			statTwo.advanceSignal();
		}
		
		if (!isDetecting())
			setTargetCourse();
	}
	
	private void setTargetCourse() {
		double x, z;
		if (Math.random() < 0.5) {
			x = sigOne.getXWithError();
			z = sigTwo.getZWithError();
		} else {
			x = sigTwo.getXWithError();
			z = sigOne.getZWithError();
		}
		Coordinate believedPosition = new Coordinate(x, 0, z);
		calculateCourse(believedPosition);
	}
	
	/*gets a leading target on the square path based on the believed position*/
	private void calculateCourse(Coordinate believedPosition) {
		targetPosition = SQUARE.getTargetCoordinate(believedPosition);
		actualPosition = Course.track(actualLocation(), targetPosition, 2);
		course = Course.track(believedPosition, targetPosition, 2);
	}
	
	//if signal within radius, set it to detected
	public boolean isDetected(Signal signal) {
		if (signal != null)
			return Coordinate.distance(signal.location(), this.location()) <= viewingDepth;
		return false;
	}
	
	private void generateSignals() {
		sigOne = null;
		statOne.generateSignal();
		sigTwo = null;
		statTwo.generateSignal();
	}
	
	//testing
	public static void main(String[] args) {
		
	}

}
