import cs480viewer.task7.AgentManager;
import cs480viewer.task7.E_TrackMode;
import cs480viewer.task7.Viewer;

public class Agent {
	private Coordinate curr;
	private double yaw = 0;
	private double pitch = 0;
	private double roll = 0;
	private double speed = 0; //in knots
	private double weight = 1000;
	private Dynamics dynamics = new Dynamics();
	private Coordinate weightDropCoord;
	
	public Agent(Coordinate start, double yaw, double speed) {
		curr = start;
		weightDropCoord = curr;
		this.yaw = yaw;
		this.speed = speed;
	}
	
	public Coordinate curr() {return curr;}
	public double x() {return curr.x();}
	public double y() {return curr.y();}
	public double z() {return curr.z();}
	public double yaw() {return yaw;}
	public double pitch() {return pitch;}
	public double roll() {return roll;}
	public double speed() {return speed;}
	public double weight() {return weight;}
	
	public void setSpeed(double s) {speed = s;}
	public void setRoll(double r) {roll = r;}
	
	public void levelFlight(double reductionFactor) {
		//get next coordinate based on speed, roll, and weight
		Coordinate prev = curr;
		curr = dynamics.nextCoord(curr, speed, roll, yaw, weight, reductionFactor);
		yaw = Coordinate.yaw(prev, curr);
		pitch = Coordinate.pitch(prev, curr);
		
		//adjust weight every 5 meters
		if (Coordinate.distance(weightDropCoord, curr) >= 10.0) {
			weight -= 5.0;
			weightDropCoord = curr;
		}
		double error = 200 - y();
		setSpeed(speed() + error);
		
	}
	
	public void climbingStraight(double reductionFactor) {
		//get next coordinate based on speed, roll, and weight
		Coordinate prev = curr;
		curr = dynamics.nextCoord(curr, speed, roll, yaw, weight, reductionFactor);
		yaw = Coordinate.yaw(prev, curr);
		pitch = Coordinate.pitch(prev, curr);
		
		//adjust weight every 5 meters
		if (Coordinate.distance(weightDropCoord, curr) >= 10.0) {
			weight -= 5.0;
			weightDropCoord = curr;
		}
		
		double error = 20.0 - pitch;
		setSpeed(speed() + error);
				
	}
	
	public void levelBanking(double reductionFactor) {
		//get next coordinate based on speed, roll, and weight
		Coordinate prev = curr;
		curr = dynamics.nextCoord(curr, speed, roll, yaw, weight, reductionFactor);
		yaw = Coordinate.yaw(prev, curr);
		pitch = Coordinate.pitch(prev, curr);
		
		//adjust weight every 5 meters
		if (Coordinate.distance(weightDropCoord, curr) >= 20.0) {
			weight -= 5.0;
			weightDropCoord = curr;
		}
		
		double error = 200 - y();
		setSpeed(speed() + 1.1 * error);
		System.out.println(speed);
	
	}
	
	public void climbingBanking(double reductionFactor) {
		//get next coordinate based on speed, roll, and weight
		Coordinate prev = curr;
		curr = dynamics.nextCoord(curr, speed, roll, yaw, weight, reductionFactor);
		yaw = Coordinate.yaw(prev, curr);
		pitch = Coordinate.pitch(prev, curr);
		
		//adjust weight every 5 meters
		if (Coordinate.distance(weightDropCoord, curr) >= 20.0) {
			weight -= 5.0;
			weightDropCoord = curr;
		}
		
		double error = 20 - pitch;
		setSpeed(speed() +  error);
		System.out.println(speed);
	
	}
	
	public static void main(String[] args) {

	}

}
