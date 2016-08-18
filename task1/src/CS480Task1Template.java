import cs480viewer.task1.Viewer;

//=============================================================================================================================================================
public class CS480Task1Template
{
   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   public static void main(final String[] args)
   {
      new CS480Task1Template();
   }

   private final Viewer _viewer;

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   public CS480Task1Template()
   {
      _viewer = new Viewer("C:\\Users\\Richard\\Desktop\\workspace\\cs480-task-1-support\\track1_template.trk");

      doTest1();
   }

   // ---------------------------------------------------------------------------------------------------------------------------------------------------------
   private void doTest1()
   {
      for (double i = 0; i < 150; i += 5)
      {
         // call this one per movement; the arguments are id (use "bird"), x, y, z, yaw, pitch, and roll
         _viewer.doAddEvent("bird", i, (i * i * 0.01), (i * i * 0.025), (i * 10), i, (i * 3));

         // call this after each movement; the argument is the delay in milliseconds
         _viewer.doAdvanceEventClock(50);
      }
   }
}
