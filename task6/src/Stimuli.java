
public class Stimuli {
	private double posX, posZ, negX, negZ;
	private final double DEFAULT_FACTOR = 1;
	private double posFactor = DEFAULT_FACTOR;
	private double negFactor = DEFAULT_FACTOR;
	private Coordinate posCoord = null;
	
	public Stimuli() {
		posX = randDouble(-500, 500);
		posZ = randDouble(-500, 500);
		posCoord = new Coordinate(posX, 0, posZ);
		
		negX = randDouble(-500, 500);
		negZ = randDouble(-500, 500);
	}
	
	public Coordinate posCoord() {return posCoord;}
	
	public double posX() {return posX;}
	public double posY() {return posUtil(0, 0);}
	public double posZ() {return posZ;}
	
	public double negX() {return negX;}
	public double negY() {return negUtil(0, 0);}
	public double negZ() {return negZ;}
	
	public double posFactor() {return posFactor;}
	public double negFactor() {return negFactor;}
	public void incrementPosFactor() {posFactor++;}
	public void decrementNegFactor() {negFactor--;}
	public void resetPosFactor() {posFactor = DEFAULT_FACTOR;}
	public void resetNegFactor() {negFactor = DEFAULT_FACTOR;}
	
	public double posUtil(double x, double z) {
		return posFactor * term(posX, x) * term(posZ, z);
	}
	
	public double negUtil(double x, double z) {
		return negFactor * 0.5 * term(negX, x) * term(negZ, z);
	}
	
	private double term(double offset, double num) {
		return Math.pow(Math.E, (-sqr(num - offset) / 30000));
	}
	
	private double sqr(double num) {
		return Math.pow(num, 2);
	}
	
	private static double randDouble(double max, double min) {
		return Math.random() * (max - min) + min;
	}
	
	public String toString() {
		return "positive : " + posX + ", " + posZ + "\n" +
			   "negative : " + negX + ", " + negZ; 
	}
}
