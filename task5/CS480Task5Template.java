import cs480viewer.task5.Agent;
import cs480viewer.task5.AgentManager;
import cs480viewer.task5.E_TrackMode;
import cs480viewer.task5.Viewer;

//=============================================================================================================================================================
public class CS480Task5Template
{
   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   public static void main(final String[] args)
   {
      new CS480Task5Template();
   }

   private final Viewer _viewer;

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   public CS480Task5Template()
   {
      _viewer = new Viewer("C:\\Users\\Richard\\Google Drive\\EWU\\CSCD480 Intelligent Systems\\workspace\\task5\\tracks\\template.trk", 0);

      //doTest1();
      //doTest2();
      //doTest3();
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   private void doTest1()
   {
      // this block provides control over displaying individual tracks; use the T key to toggle all on and off simultaneously. It applies to record mode only;
      // your settings are not retained for grading
      // {
      AgentManager agentManager = _viewer.getAgentManager();

      Agent agentbat = agentManager.getAgent("pulse");

      agentbat.setTrackMode(E_TrackMode.OFF); // keep the pulse track off; otherwise, the display will become royal mess
      // }

      int v;

      for (int i = 0; i < 30; ++i)
      {
         v = (i * 5);

         _viewer.doAddEvent("bat", -50 + v, 0, -v * 3, i * 12, 0, 0);

         if (i < 10)
         {
            _viewer.doAddEvent("bug1", +100 - v, 0, v * 3, 10 + (i * 3), 0, 0);
         }

         _viewer.doAddEvent("bug2", +150, 0, v, 20 + (i * 5), 0, 0);
         _viewer.doAddEvent("bug3", +200, 0, v * 2, 30 + (i * 7), 0, 0);
         _viewer.doAddEvent("bug4", +250 + v, 0, v, 40 + (i * 9), 0, 0);

         _viewer.doAddEvent("pulse", v * 3, 0, -v * 3, 0, 0, 0);

         if (i == 5)
         {
            _viewer.doHighlightAgent("bug2"); // changes the entity's color to orange for several clock ticks
         }

         _viewer.doAdvanceEventClock();

         if (i == 10)
         {
            _viewer.doDecommissionAgent("bug1"); // removes the agent from usage; do not call doAddEvent() for it anymore
         }
      }
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   private void doTest2()
   {
      _viewer.getAgentManager();

      // agentManager.getAgent("pulse").setTrackMode(E_TrackMode.OFF); // keep the pulse track off; otherwise, the display will become royal mess
      // agentManager.getAgent("bug1").setTrackMode(E_TrackMode.OFF); // keep the pulse track off; otherwise, the display will become royal mess
      // agentManager.getAgent("bug2").setTrackMode(E_TrackMode.OFF); // keep the pulse track off; otherwise, the display will become royal mess
      // agentManager.getAgent("bug3").setTrackMode(E_TrackMode.OFF); // keep the pulse track off; otherwise, the display will become royal mess
      // agentManager.getAgent("bug4").setTrackMode(E_TrackMode.OFF); // keep the pulse track off; otherwise, the display will become royal mess
      // agentManager.getAgent("bat").setTrackMode(E_TrackMode.OFF); // keep the pulse track off; otherwise, the display will become royal mess
      // }

      long timeStart = System.currentTimeMillis();

      int v;

      for (int i = 0; i < 100; ++i)
      {
         v = (int) (Math.random() * 100);

         _viewer.doAddEvent("bat", -50 + v, 0, -v * 3, i * 12, 0, 0);

         if (i < 10)
         {
            _viewer.doAddEvent("bug1", +100 - v, 0, v * 3, 10 + (i * 3), 0, 0);
         }

         _viewer.doAddEvent("bug2", +150, 0, v, 20 + (i * 5), 0, 0);
         _viewer.doAddEvent("bug3", +200, 0, v * 2, 30 + (i * 7), 0, 0);
         _viewer.doAddEvent("bug4", +250 + v, 0, v, 40 + (i * 9), 0, 0);

         _viewer.doAddEvent("pulse", v * 3, 0, -v * 3, 0, 0, 0);

         if (i == 5)
         {
            _viewer.doHighlightAgent("bug2"); // changes the entity's color to orange for several clock ticks
         }

         _viewer.doAdvanceEventClock();

         if (i == 10)
         {
            _viewer.doDecommissionAgent("bug1"); // removes the agent from usage; do not call doAddEvent() for it anymore
         }

         if ((i % 1000) == 0)
         {
            System.out.println(i);
         }
      }

      long timeEnd = System.currentTimeMillis();

      System.out.println("time=" + (timeEnd - timeStart));
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   private void doTest3()
   {
      long timeStart = System.currentTimeMillis();

      boolean isDecommissioned = false;

      _viewer.doAddEvent("bug1", +100, 0, 50, 10, 0, 0);

      for (int angle = 0; angle < 360; angle += 1)
      {
         System.out.println("angle=" + angle);

         for (int distance = 0; distance < 250; distance += 5)
         {
            double x = (Math.sin(Math.toRadians(angle)) * distance);
            double z = (Math.cos(Math.toRadians(angle)) * distance);

            boolean updateViewer = (distance == 249);

            double threshold = (1 - ((distance * distance) / (double) (500 * 500)));

            if (Math.random() > threshold)
            {
               updateViewer = true;

               _viewer.doHighlightAgent("bug1");

               _viewer.doAdvanceEventClock();
            }
            else
            {
               _viewer.doDehighlightAgent("bug1");
            }

            if (angle < 350)
            {
               _viewer.doAddEvent("pulse", x, 0, z, 0, 0, 0, updateViewer);
            }
            else if ((angle == 350) && !isDecommissioned)
            {
               isDecommissioned = true;

               _viewer.doDecommissionAgent("pulse");
            }
         }

         _viewer.doAdvanceEventClock();
      }

      _viewer.doAdvanceEventClock();

      long timeEnd = System.currentTimeMillis();

      System.out.println("time=" + (timeEnd - timeStart));
   }
}
