import cs480viewer.task7.AgentManager;
import cs480viewer.task7.E_TrackMode;
import cs480viewer.task7.Viewer;

//=============================================================================================================================================================
public class CS480Task7Template
{
   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   public static void main(final String[] args)
   {
      new CS480Task7Template();
   }


   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   public CS480Task7Template()
   {
      doTest1();
      doTest2();
	  doTest3();
	  doTest4();
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   private void doTest1()
   {
		Viewer _viewer = new Viewer("C:\\Users\\Richard\\Google Drive\\EWU\\CSCD480 Intelligent Systems\\workspace\\task7\\tracks\\tracks7_test.trk", 20);

		AgentManager agentManager = _viewer.getAgentManager();

		agentManager.getAgent("uap").setTrackMode(E_TrackMode.HORIZONTAL_VERTICAL); // also consider E_TrackMode.HORIZONTAL
		
		Coordinate start = new Coordinate(-500, 200, -500);
		Agent a = new Agent(start, 45, 120);
		

		while (a.z() < 480)
		{
			_viewer.doAddEvent("uap", a.x(), a.y(), a.z(), a.yaw(), a.pitch(), a.roll());

			_viewer.doAdvanceEventClock();
			
			a.levelFlight(3);
		}
   }
   
   private void doTest2() {
		Viewer _viewer = new Viewer("C:\\Users\\Richard\\Google Drive\\EWU\\CSCD480 Intelligent Systems\\workspace\\task7\\tracks\\tracks7_test.trk", 20);

		AgentManager agentManager = _viewer.getAgentManager();

		agentManager.getAgent("uap").setTrackMode(E_TrackMode.HORIZONTAL_VERTICAL); // also consider E_TrackMode.HORIZONTAL
		
		Coordinate start = new Coordinate(-500, 200, -500);
		Agent a = new Agent(start, 45, 120);
		

		while (a.z() < 490)
		{
			_viewer.doAddEvent("uap", a.x(), a.y(), a.z(), a.yaw(), a.pitch(), a.roll());

			_viewer.doAdvanceEventClock();
			
			a.climbingStraight(20);
		}
	   
   }
   
   private void doTest3() {
		Viewer _viewer = new Viewer("C:\\Users\\Richard\\Google Drive\\EWU\\CSCD480 Intelligent Systems\\workspace\\task7\\tracks\\tracks7_test.trk", 20);

		AgentManager agentManager = _viewer.getAgentManager();

		agentManager.getAgent("uap").setTrackMode(E_TrackMode.HORIZONTAL_VERTICAL); // also consider E_TrackMode.HORIZONTAL
		
		Coordinate start = new Coordinate(100, 200, -150);
		Agent a = new Agent(start, 45, 120);
		a.setRoll(-45);

		for (int i = 0; i < 500; i++)
		{
			_viewer.doAddEvent("uap", a.x(), a.y(), a.z(), a.yaw(), a.pitch(), a.roll());

			_viewer.doAdvanceEventClock();
			
			a.levelBanking(30);
		}
   }
   
   private void doTest4() {
		Viewer _viewer = new Viewer("C:\\Users\\Richard\\Google Drive\\EWU\\CSCD480 Intelligent Systems\\workspace\\task7\\tracks\\tracks7_test.trk", 20);

		AgentManager agentManager = _viewer.getAgentManager();

		agentManager.getAgent("uap").setTrackMode(E_TrackMode.HORIZONTAL_VERTICAL); // also consider E_TrackMode.HORIZONTAL
		
		Coordinate start = new Coordinate(90, 200, -100);
		Agent a = new Agent(start, 45, 120);
		a.setRoll(-45);

		for (int i = 0; i < 500; i++)
		{
			_viewer.doAddEvent("uap", a.x(), a.y(), a.z(), a.yaw(), a.pitch(), a.roll());

			_viewer.doAdvanceEventClock();
			
			a.climbingBanking(100);
		}
   }
}
