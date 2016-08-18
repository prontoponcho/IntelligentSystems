import cs480viewer.task5.Agent;
import cs480viewer.task5.AgentManager;
import cs480viewer.task5.E_TrackMode;
import cs480viewer.task5.Viewer;

public class CS480Task5 {
	public static void main(final String[] args)
	{
		new CS480Task5();
	}

	private final Viewer _viewer;

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------
	public CS480Task5()
	{
		_viewer = new Viewer("C:\\Users\\Richard\\Google Drive\\EWU\\CSCD480 Intelligent Systems\\workspace\\task5\\tracks\\tracks5_1_test.trk", 0);

		doTest1();
		doTest4();
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------
	private void doTest1()
	{
		MyAgent[] bugs = new MyAgent[4];
		for (int i = 0; i < bugs.length; i++) {
			bugs[i] = new MyAgent();
		}
		
		
		for (int count = 0; count < 1000; count++) {
			for (int i = 0; i < bugs.length; i++) {
				String buggy = "bug" + (i + 1);
				_viewer.doAddEvent(buggy, bugs[i].x(), bugs[i].y(), bugs[i].z(), bugs[i].yaw(), 0, 0);
				bugs[i].advance();
			}

			_viewer.doAdvanceEventClock();

		}
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------
	private void doTest4() {
		Viewer _viewer = new Viewer("C:\\Users\\Richard\\Google Drive\\EWU\\CSCD480 Intelligent Systems\\workspace\\task5\\tracks\\track5_4_test.trk", 0);
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
		
		for (int count = 0; count < 5000; count++) {

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
