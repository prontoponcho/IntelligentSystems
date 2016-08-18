import cs480viewer.task2.Viewer;

public class FlippingAgent extends AgentDetector {
	int flipCount = 0; //greater than zero when flipping
	
	public FlippingAgent(AgentLocation subject) {
		super(subject);
	}
	
	@Override
	public double pitch() { return Orientation.pitch(course.get(curr), course.get(curr + 1)) + 0.5;}
	
	@Override
	public void advance() {
		if (flipCount == 0 && isFlipping()) {
			flip();
		} else {
			if (flipCount > 0)
				flipCount--;
			super.advance();
		}
	}
	
	private void flip() {
		double height = Math.random() * (40 - 10) + 10;
		double frequency = 2;
		course = Course.verticalArc(height, frequency, this.location(), this.yaw());
		curr = 0;
		flipCount = course.size();
	}
	
	//switch for flipping
	private boolean isFlipping() {
		return Math.random() < 0.01;
	}
	
	//testing
	public static void main(String[] args) {
		Viewer _viewer = new Viewer("C:\\Users\\Richard\\Google Drive\\EWU\\CSCD480 Intelligent Systems\\workspace\\task2\\tracks\\task2_detector.trk", 5);
		AgentLocation subject = new AgentLocation();
		Agent[] fish = new Agent[5];
		for (int i = 0; i < fish.length; i++) {
			fish[i] = new AgentObserver(subject);

		}
		Agent dolphin = new FlippingAgent(subject);
 
		while(true) {
			for (int j = 0; j < fish.length; j++) {
				String fishy = "fish" + (j + 1);
				_viewer.doAddEvent(fishy, fish[j].x(), fish[j].y(), fish[j].z(), fish[j].yaw(), fish[j].pitch(), 0);
				fish[j].advance();
			}
			_viewer.doAddEvent("dolphin", dolphin.x(), dolphin.y(), dolphin.z(), dolphin.yaw(), dolphin.pitch(), 0);
			dolphin.advance();
			_viewer.doAdvanceEventClock(); 
		}
	}

}
