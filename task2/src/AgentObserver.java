import java.util.ArrayList;
import cs480viewer.task2.Viewer;

public class AgentObserver extends Agent{
	private AgentLocation subject = null;
	
	public AgentObserver(AgentLocation subject) {
		super();
		this.subject = subject;
		subject.registerAgent(this);
	}
	
	public AgentObserver(AgentLocation subject, ArrayList<Coordinate> course) {
		super(course);
		this.subject = subject;
		subject.registerAgent(this);
	}
	
	@Override
	public void advance() {
		if (subject.isCollision(this)) {
			reorient();
			for(int i = 0; i < 10; i++) { //create space between agent and collision
				super.advance();
			}
		} else {
			super.advance();
		}
	}
	
	private void reorient() {
		course = Course.redirect(location(), yaw());
		curr = 0;
	}
	
	//testing
	public static void main(String[] args) {
	}

}
