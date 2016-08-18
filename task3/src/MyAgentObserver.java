import java.util.ArrayList;

//Any object that needs to avoid collisions
public class MyAgentObserver extends MyAgent {
	
	public MyAgentObserver(MyAgentLocation subject) {
		super(subject);
	}
	
	public MyAgentObserver(MyAgentLocation subject, ArrayList<Coordinate> course) {
		super(subject, course);
		this.subject = subject;
	}
	
	@Override
	public void advance() {
		if (subject.isCollision(this)) {
			reorient();
			for(int i = 0; i < 5; i++) { //create space between agent and collision
				super.advance();
			}
		} else {
			super.advance();
		}
	}
	
	protected void reorient() {
		course = Course.redirect(location(), yaw());
		curr = 0;
	}
	
	//testing
	public static void main(String[] args) {
//		Viewer _viewer = new Viewer("C:\\Users\\Richard\\Google Drive\\EWU\\CSCD480 Intelligent Systems\\workspace\\task2\\tracks\\task2_2.trk", 100);
//		AgentLocation subject = new AgentLocation();
//		Agent a1 = new AgentObserver(subject);
//		Agent a2 = new AgentObserver(subject);


	}
}
