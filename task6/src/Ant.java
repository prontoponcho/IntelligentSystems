import java.util.ArrayList;

public class Ant {
	private Coordinate curr, prev;
	private Stimuli stimuli;
	private boolean atFood = false;
	
	public Ant(Stimuli stimuli) {
		this.stimuli = stimuli;
		prev = Coordinate.randomCoord();
		curr = new Coordinate(prev.x() + 0.1, prev.y(), prev.z() + 0.1);
	}
	
	public double x() {return curr.x();}
	public double y() {return curr.y();}
	public double z() {return curr.z();}
	public double yaw() {return prev.yaw(curr);}
	public double pitch() { return prev.pitch(curr);}
	public double roll() {return 0;}
	public boolean atFood() {return atFood;}
	
	public boolean advance() {
		Coordinate next = foodAdvance();
		if (next == null) {
			next = pestAdvance();
		}
		if (next == null)
			return false;
		prev = curr;
		curr = next;
		atFood = curr.distance(stimuli.posCoord()) < 5;
		return true;
	}

	private Coordinate pestAdvance() {
		while (stimuli.negFactor() > -50) {
			ArrayList<Coordinate> probes = negProbes();
			Coordinate highest = highestCoord(probes);
			if ((int)highest.y() > (int)this.y()) {
				stimuli.resetNegFactor();
				return highest;
			} else {
				System.out.println("decrementing neg factor");
				stimuli.decrementNegFactor();
			}
		} 
		stimuli.resetNegFactor();
		return null;
	}

	private Coordinate foodAdvance() {
		while (stimuli.posFactor() < 50) {
			ArrayList<Coordinate> probes = posProbes();
			Coordinate highest = highestCoord(probes);
			if ((int)highest.y() > (int)this.y()) {
				stimuli.resetPosFactor();
				return highest;
			} else {
				System.out.println("incrementing pos factor");
				stimuli.incrementPosFactor();
			}
		} 
		stimuli.resetPosFactor();
		return null;
	}
	
	private ArrayList<Coordinate> posProbes() {
		ArrayList<Coordinate> probes = new ArrayList<Coordinate>();
		for (int i = 0; i < 360; i++) {
			Coordinate probe = probePositiveStimuli(i);
			probes.add(probe);
		}
		return probes;
	}
	
	private ArrayList<Coordinate> negProbes() {
		ArrayList<Coordinate> probes = new ArrayList<Coordinate>();
		for (int i = 0; i < 360; i++) {
			Coordinate probe = probeNegativeStimuli(i);
			probes.add(probe);
		}
		return probes;
	}
	
	private Coordinate probePositiveStimuli(double direction) {
		double probeZ = 5 * Math.cos(Math.toRadians(direction)) + z();
		double probeX = 5 * Math.sin(Math.toRadians(direction)) + x();
		return new Coordinate(probeX, stimuli.posUtil(probeX, probeZ), probeZ);
	}
	
	private Coordinate probeNegativeStimuli(double direction) {
		double probeZ = 5 * Math.cos(Math.toRadians(direction)) + z();
		double probeX = 5 * Math.sin(Math.toRadians(direction)) + x();
		return new Coordinate(probeX, stimuli.negUtil(probeX, probeZ), probeZ);
	}
	
	private Coordinate highestCoord(ArrayList<Coordinate> coords) {
		Coordinate maxY = coords.get(0);
		for (Coordinate curr : coords) {
			if (maxY.y() < curr.y())
				maxY = curr;
		}
		return maxY;
	}
}
