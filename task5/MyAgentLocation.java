import java.util.ArrayList;

public class MyAgentLocation {
		private ArrayList<Bug> bugs;

		public MyAgentLocation() {
			this.bugs = new ArrayList<Bug>();
		}
		
		public ArrayList<Bug> getAgents() {return bugs;}
		
		public void registerAgent(Bug a) {
			if (bugs.contains(a))
				throw new IllegalArgumentException("agent already registered");
			else
				bugs.add(a);
		}
		
		public void unregister(Bug a) {
			bugs.remove(a);
		}
			
		public Bug detect(Pulse pulse) {
			for (Bug bug : bugs) {
				if (Coordinate.distance(pulse.location(), bug.location()) < 10)
					return bug;
			}
			return null;
		}
		
		//testing
		public static void main(String[] args) {

		}
	}

