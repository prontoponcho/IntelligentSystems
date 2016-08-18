import java.util.ArrayList;

public class Flower extends MyAgent {
	
	public Flower(ArrayList<Coordinate> course) {
		super(course);
	}
	
	public Flower() {
		super();
	}
	
	@Override
	public void advance() {
		//never advance
	}
	
	@Override
	public String toString() {
		return "flower";
	}

}
