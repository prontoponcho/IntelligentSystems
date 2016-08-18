
public class Station {
		private Coordinate location = null;
		private Signal signal = null;
		private Blimp blimp = null;
		
		public Station(Coordinate location) {
			System.out.println("station's location: " + location.toString());
			this.location = location;
		}
		
		//getters & setters
		public Coordinate location() {return this.location;}
		public Signal signal() {return this.signal;}
		public void setBlimp(Blimp blimp) {this.blimp = blimp;}
		
		public void advanceSignal() {
			if (signal != null)
				signal.advance();
			else
				generateSignal();
		}
		
		public void generateSignal() {
			signal = new Signal(this.location, blimp.location());
		}
		
		public void stopSignal() {
			signal = null;
		}
		
		
}
