import cs480viewer.task5.Agent;
import cs480viewer.task5.AgentManager;
import cs480viewer.task5.E_TrackMode;
import cs480viewer.task5.Viewer;

//=============================================================================================================================================================
public class CS480Task6Template
{
   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   public static void main(final String[] args)
   {
      new CS480Task6Template();
   }

   private final Viewer _viewer;

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   public CS480Task6Template()
   {
      _viewer = new Viewer("C:\\Users\\Richard\\Google Drive\\EWU\\CSCD480 Intelligent Systems\\workspace\\task6\\tracks\\track6_8.trk", 50);

      doTest1();
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   private void doTest1()
   {
      AgentManager agentManager = _viewer.getAgentManager();

      Agent agentAnt = agentManager.getAgent("ant");

      agentAnt.setTrackMode(E_TrackMode.HORIZONTAL);
      
      Stimuli stimuli = new Stimuli();
      Ant ant = new Ant(stimuli);
      
      boolean advancing = true;
      while (true) {
    	  _viewer.doAddEvent("food", stimuli.posX(), stimuli.posY(), stimuli.posZ(), 0, 0, 0);
    	  _viewer.doAddEvent("pesticide", stimuli.negX(), stimuli.negY(), stimuli.negZ(), 0, 0, 0);
    	  _viewer.doAddEvent("ant", ant.x(), ant.y(), ant.z(), ant.yaw(), ant.pitch(), ant.roll());
    	  advancing = ant.advance();
    	  
    	  if (!advancing) {
    		  System.out.println("DIDN'T ADVANCE!");
    		  ant = new Ant(stimuli);
    	  }
    	  if (ant.atFood()) System.exit(0);;
    	  _viewer.doAdvanceEventClock();

      }
   }
}
