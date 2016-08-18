import java.util.ArrayList;

import cs480viewer.task5.Agent;
import cs480viewer.task5.AgentManager;
import cs480viewer.task5.E_TrackMode;
import cs480viewer.task5.Viewer;

//=============================================================================================================================================================
public class CS480Task4
{
   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   public static void main(final String[] args)
   {
      new CS480Task4();
   }

   private final Viewer _viewer;

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   public CS480Task4()
   {
      _viewer = new Viewer("C:\\Users\\Richard\\Google Drive\\EWU\\CSCD480 Intelligent Systems\\workspace\\task4\\tracks\\task4_test.trk", 1);

      doTest1();
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   private void doTest1()
   {
      AgentManager agentManager = _viewer.getAgentManager();

      Agent agentBlimpActual = agentManager.getAgent("blimp-actual");
      Agent agentBlimpBelieved = agentManager.getAgent("blimp-believed");

      agentBlimpActual.setTrackMode(E_TrackMode.HORIZONTAL);
      agentBlimpBelieved.setTrackMode(E_TrackMode.HORIZONTAL);

      _viewer.doAddEvent("station1", 0, 0, -500, 0, 0, 0);
      _viewer.doAddEvent("station2", -500, 0, 500, 0, 0, 0);
      
      Station one = new Station(new Coordinate(0, 0, -500));
      Station two = new Station(new Coordinate(-500, 0, 500));
      ArrayList<Station> stations = new ArrayList<Station>();
      stations.add(two); stations.add(one);
      
      Coordinate start = new Coordinate(-200, 0, -200);
      Blimp blimp = new Blimp(start, one, two);
      
      one.setBlimp(blimp);
      two.setBlimp(blimp);

      while (true)
      {
         _viewer.doAddEvent("blimp-actual", blimp.actualX(), blimp.actualY(), blimp.actualZ(), blimp.actualYaw(), 0, 0);
         _viewer.doAddEvent("blimp-believed", blimp.x(), blimp.y(), blimp.z(), blimp.yaw(), 0, 0);
         blimp.advance();
         if (one.signal() != null) {
        	 //_viewer.doAddEvent("ray1", one.signal().x(), one.signal().y(), one.signal().z(), one.signal().yaw(), 0, 0);
        	 one.advanceSignal();
         }
         if (two.signal() != null) {
        	 //_viewer.doAddEvent("ray2", two.signal().x(), two.signal().y(), two.signal().z(), two.signal().yaw(), 0, 0);
        	 two.advanceSignal();
         }
         
         _viewer.doAdvanceEventClock();
      }
   }
}
