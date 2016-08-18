import cs480viewer.task2.Viewer;

public class CS480Task2 {
	private static Viewer _viewer = null;
	
	public CS480Task2() {
//		doPart1();
//		doPart2();
//		doPart3();
		doPart4();
	}
	
	public static void doPart1() {
		_viewer = new Viewer("C:\\Users\\Richard\\Google Drive\\EWU\\CSCD480 Intelligent Systems\\workspace\\task2\\tracks\\task2_1.trk", 10);
		Agent[] fish = initAgents(5);
		visualize(fish);
	}
	
	public static void doPart2() {
		_viewer = new Viewer("C:\\Users\\Richard\\Google Drive\\EWU\\CSCD480 Intelligent Systems\\workspace\\task2\\tracks\\task2_2.trk", 10);
		AgentLocation subject = new AgentLocation();
		Agent[] fish = initAgentObservers(subject);
		visualize(fish);
	}
	
	public static void doPart3() {
		_viewer = new Viewer("C:\\Users\\Richard\\Google Drive\\EWU\\CSCD480 Intelligent Systems\\workspace\\task2\\tracks\\task2_3.trk", 10);
		AgentLocation subject = new AgentLocation();
		Agent[] fish = initAgentObservers(subject);
		Agent dolphin = new AgentDetector(subject);
		visualize(fish, dolphin);
	}
	
	public static void doPart4() {
		_viewer = new Viewer("C:\\Users\\Richard\\Google Drive\\EWU\\CSCD480 Intelligent Systems\\workspace\\task2\\tracks\\task2_4.trk", 10);
		AgentLocation subject = new AgentLocation();
		Agent[] fish = initAgentObservers(subject);
		Agent dolphin = new FlippingAgent(subject);
		visualize(fish, dolphin);
	}
	
	public static Agent[] initAgents(int x) {
		if (x < 0)
			throw new IllegalArgumentException("negative array size");
		Agent[] agents = new Agent[x];
		for (int i = 0; i < x; i++)
			agents[i] = new Agent();
		return agents;
	}
	
	public static Agent[] initAgentObservers(AgentLocation subject) {
		Agent[] agents = new Agent[5];
		for (int i = 0; i < 5; i++) {
			agents[i] = new AgentObserver(subject);
			//System.out.println("agent " + i + " " + agents[i].location().toString());
		}
		return agents;
	}
	
	private static void visualize(Agent[] fish) {
		System.out.println("running viewer");
		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < fish.length; j++) {
				String fishy = "fish" + (j + 1);
				_viewer.doAddEvent(fishy, fish[j].x(), fish[j].y(), fish[j].z(), fish[j].yaw(), 0, 0);			
				fish[j].advance();
			}
			_viewer.doAdvanceEventClock(); 
		}
	}
	
	private static void visualize(Agent[] fish, Agent dolphin) {
		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < fish.length; j++) {
				String fishy = "fish" + (j + 1);
				_viewer.doAddEvent(fishy, fish[j].x(), fish[j].y(), fish[j].z(), fish[j].yaw(), 0, 0);			
				fish[j].advance();
			}
			_viewer.doAddEvent("dolphin", dolphin.x(), dolphin.y(), dolphin.z(), dolphin.yaw(), dolphin.pitch(), 0);
			dolphin.advance();
			_viewer.doAdvanceEventClock(); 
		}
	}
	
	public static void main(final String[] args){
		new CS480Task2();
	}
}
