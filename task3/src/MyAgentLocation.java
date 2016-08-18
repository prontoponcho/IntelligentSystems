import java.util.ArrayList;

import cs480viewer.task3.Viewer;

//Maintains a list of agents for collision detection.
//To be used as the subject in the Observer pattern.
public class MyAgentLocation {
	private ArrayList<MyAgent> agents;
	private MyAgent collidingAgent = null;
	private Viewer viewer;
	
	public MyAgentLocation(Viewer viewer) {
		this.agents = new ArrayList<MyAgent>();
		this.viewer = viewer;
	}
	
	public void registerAgent(MyAgent a) {
		if (agents.contains(a))
			throw new IllegalArgumentException("agent already registered");
		else
			agents.add(a);
	}
	
//	public void removeAgent(MyAgent a) {
//		agents.remove(a);
//	}
	
	public boolean isCollision(MyAgent a1) {
		for (MyAgent a2 : agents) {
			if(a1 != a2) {
				if (Coordinate.distance(a1.location(), a2.location()) < 20) {
					collidingAgent = a2;
					return true;
				}
			}
		}
		return false;
	}
	
	public MyAgent getCollidingAgent() {
		return collidingAgent;
	}
	
	public MyAgent getRegistrant(Coordinate coord) {
		for (MyAgent agent : agents) {
			if (agent.location().equals(coord))
				return agent;
		}
		return null;
	}
	
	//return defensive copy of registrants coordinates
	public ArrayList<Coordinate> getRegisterantLocations() {
		ArrayList<Coordinate> listCopy = new ArrayList<Coordinate>();
		for (MyAgent a : agents) {
			Coordinate c = a.location();
			Coordinate copy = new Coordinate(c.getX(), c.getY(), c.getZ());
			listCopy.add(copy);
		}
		return listCopy;
	}
	
	public MyAgent detect(MyAgentDetector detector) {
		for (MyAgent agent : agents) {
			if (agent != detector && detector.inView(agent.location()))
				return agent;
		}
		return null;
	}
	
//	public void advanceAgents() {
//		for (MyAgent agent : agents)
//			agent.advance();
//	}
//	
//	public void addEvents() {
//		for (MyAgent agent : agents) {
//			viewer.doAddEvent(agent.toString(), 
//							  agent.x(), 
//							  agent.y(), 
//							  agent.z(),
//							  agent.yaw(), 
//							  agent.pitch(), 
//							  agent.roll());
//		}
//		viewer.doAdvanceEventClock();
//	}
//	
//	public void advanceClock() {
//		viewer.doAdvanceEventClock();
//	}
	
	//testing
	public static void main(String[] args) {
		Viewer _viewer = new Viewer("C:\\Users\\Richard\\Google Drive\\EWU\\CSCD480 Intelligent Systems\\workspace\\task3\\tracks\\task3_test.trk", 10);
		MyAgentLocation subject = new MyAgentLocation(_viewer);
		
		//course one
		ArrayList<Coordinate> course1 = new ArrayList<Coordinate>();
		course1.add(new Coordinate(50,0,0));
		course1.add(new Coordinate(-50,0,0));
		course1 = Course.intermediates(course1, 3);
		MyAgentObserver a1 = new MyAgentObserver(subject, course1);
		
		//colliding course two
		ArrayList<Coordinate> course2 = new ArrayList<Coordinate>();
		course2.add(new Coordinate(-50,0,0));
		course2.add(new Coordinate(50,0,0));
		course2 = Course.intermediates(course2, 3);
		MyAgentObserver a2 = new MyAgentObserver(subject, course2);
		
		ArrayList<Coordinate> course3 = new ArrayList<Coordinate>();
		course3.add(new Coordinate(0,0,-50));
		course3.add(new Coordinate(0,0,50));
		course3 = Course.intermediates(course3, 3);
		MyAgentObserver a3 = new MyAgentObserver(subject, course3);
		
		ArrayList<Coordinate> course4 = new ArrayList<Coordinate>();
		course4.add(new Coordinate(0,0,50));
		course4.add(new Coordinate(0,0,-50));
		course4 = Course.intermediates(course4, 3);
		MyAgentObserver a4 = new MyAgentObserver(subject, course4);
		
		MyAgent[] agents = {a1, a2, a3, a4};
		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < agents.length; j++) {
				String bee = "bee" + (j + 1);
				_viewer.doAddEvent(bee, agents[j].x(), agents[j].y(), agents[j].z(), agents[j].yaw(), 0, 0);			
				agents[j].advance();
			}
			_viewer.doAdvanceEventClock(); 
		}
	}
}
