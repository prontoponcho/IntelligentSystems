import java.util.ArrayList;

import cs480viewer.task5.AgentManager;
import cs480viewer.task5.E_TrackMode;
import cs480viewer.task5.Viewer;

public class Bat extends MyAgent {
	private String name;
	private boolean tracking;
	private boolean locating;
	private ArrayList<Bug> bugs = new ArrayList<Bug>();
	private ArrayList<Pulse> pulses = null;
	private Bug targetBug = null;
	private MyAgentLocation myAgentLocation = null;
	
	public Bat(String name, MyAgentLocation mal) {
		super();
		this.name = name;
		tracking = false;
		locating = true;
		myAgentLocation = mal;
	}
	
	public Bat(String name, ArrayList<Coordinate> c, MyAgentLocation mal) {
		super(c);
		this.name = name;
		tracking = false;
		locating = true;
		myAgentLocation = mal;
	}
	
	public String name() {return name;}
	public ArrayList<Bug> bugs() {return bugs;}
	public ArrayList<Pulse> pulses() {return pulses;}
	
	@Override
	public void advance() {
		if (locating) {
			locate();
		} else if (tracking) {
			track();
		} else {
			super.advance();
		}
	}

	private void locate() {
		
		if (pulses == null) { //propogate new pulses
			pulses = new ArrayList<Pulse>();
			for (int i = 0; i < 360; i++)
				pulses.add(new Pulse(location(), (i)));
		}
		
		for (int i = 0; i < pulses.size(); i++) { //remove pulses that ended
			pulses.get(i).advance();
			if (pulses.get(i).endOfCourse()) {
				pulses.remove(i);
				i--;
			}	
		}
		
		for (Pulse pulse : pulses) { //check if pulses hit bugs
			Bug bug = myAgentLocation.detect(pulse); 
			if (bug != null) { //attenuated echolocation : true positive
				double distance = Coordinate.distance(location(), pulse.location());
				double pTP = (62500 - Math.pow(distance, 2)) / 62500;
				if (Math.random() < pTP) {
					System.out.println("real bug added");
					if (!bugs.contains(bug)) {
						pulse.setDetectedBug(true);
						bugs.add(bug);
					}
				}
			} else if (pulse.curr > 400){ //attenuated echolocation : false positive
				System.out.println("potential false positive");
				double distance = Coordinate.distance(location(), pulse.location());
				double pFP = 0.2 * (1 - (62500 - Math.pow(distance, 2)) / 62500);
				if (Math.random() < pFP) { //add fake bug
					System.out.println("fake bug added!");
					Bug fake = new Bug("fake");
					bugs.add(fake);
				}
			}
		}

		if (pulses.size() == 0) {
			pulses = null;
			if (bugs.size() > 0) {
				tracking = true;
				locating = false;
			}
		}
		super.advance();
	}
	
	private void track() {
		if (targetBug == null)
			targetBug = getClosestBug();
		if (targetBug == null) { //if still no target
			tracking = false;
			return;
		} else {
			for (Bug bug : bugs) {
				if (bug != null && bug != targetBug)
					bug.setDetected(false);
			}
			if (isDistantTarget(targetBug)) {
				course = Course.track(this.location(), targetBug.location(), 2.3);
				curr = 0;
			} else {
				targetBug.setEaten(true);
				targetBug = null;
				bugs = new ArrayList<Bug>();
				tracking = false;
				locating = true;
			}
		}
	}

	private boolean isDistantTarget(Bug bug) {
		return Coordinate.distance(bug.location(), this.location()) > 10;
	}
	
	private Bug getClosestBug() {
		Bug closestBug = null;
		double closestDist = Double.MAX_VALUE;
		for (Bug bug : bugs) {
			if (bug != null) {
				double currDist = Coordinate.distance(this.location(), bug.location());
				if (currDist < closestDist) {
					closestDist = currDist;
					closestBug = bug;
				}
			}
		}
		return closestBug;
	}
	public static void main(String[] args) {
		Viewer _viewer = new Viewer("C:\\Users\\Richard\\Google Drive\\EWU\\CSCD480 Intelligent Systems\\workspace\\task5\\tracks\\track5_3_test.trk", 0);
		AgentManager agentManager = _viewer.getAgentManager();
		agentManager.getAgent("pulse").setTrackMode(E_TrackMode.OFF); // keep the pulse track off; otherwise, the display will become royal mess
		 agentManager.getAgent("bug1").setTrackMode(E_TrackMode.OFF); // keep the pulse track off; otherwise, the display will become royal mess
		 agentManager.getAgent("bug2").setTrackMode(E_TrackMode.OFF); // keep the pulse track off; otherwise, the display will become royal mess
		 agentManager.getAgent("bug3").setTrackMode(E_TrackMode.OFF); // keep the pulse track off; otherwise, the display will become royal mess
		 agentManager.getAgent("bug4").setTrackMode(E_TrackMode.OFF); // keep the pulse track off; otherwise, the display will become royal mess
		

		MyAgentLocation mal = new MyAgentLocation();
		Bat bat = new Bat("bat", mal);
		
		Bug bug = new Bug("bug1");
		mal.registerAgent(bug);
		
		Bug bug2 = new Bug("bug2");
		mal.registerAgent(bug2);
		
		Bug bug3 = new Bug("bug3");
		mal.registerAgent(bug3);
		
		Bug bug4 = new Bug("bug4");
		mal.registerAgent(bug4);
		
		while (true) {

			for (Bug buggy : mal.getAgents()) {
				_viewer.doAddEvent(buggy.name(), buggy.x(), buggy.y(), buggy.z(), buggy.yaw(), 0, 0);
				buggy.advance();
			}
			
			_viewer.doAddEvent(bat.name(), bat.x(), bat.y(), bat.z(), bat.yaw(), 0, 0);
			bat.advance();
			
			
			if (bat.bugs().size() > 0) {
				for (Bug detectedBug : bat.bugs()) {
					if (!detectedBug.name().equals("fake"))
						_viewer.doHighlightAgent(detectedBug.name());
				}
			}
			
			for (int i = 0; i < mal.getAgents().size(); i++) {
				Bug buggy = mal.getAgents().get(i);
				if (buggy.isEaten()) {
					_viewer.doDecommissionAgent(buggy.name());
					mal.unregister(buggy);
				}
			}
			
			_viewer.doAdvanceEventClock();
		}
	}
}
