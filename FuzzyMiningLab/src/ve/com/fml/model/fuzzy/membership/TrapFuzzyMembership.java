package ve.com.fml.model.fuzzy.membership;

public class TrapFuzzyMembership extends FuzzyMembership {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double lowerBound;
	private double upperBound;
	private double topTrap1;
	private double topTrap2;
	private double maxY = 1;
	
	public TrapFuzzyMembership(double lower, double upper, double top1, double top2){
		lowerBound = lower;
		upperBound = upper;
		topTrap1 = top1;
		topTrap2 = top2;
	}
	
	public TrapFuzzyMembership(double lower, double upper, double top1, double top2, double maxY){
		this(lower,upper,top1,top2);
		this.maxY = maxY;
	}
	
	@Override
	public double fuzzyEval(double value) {
		double result = 0;
		if(value > lowerBound && value <= topTrap1)
			result = maxY*(value - lowerBound)/(topTrap1 - lowerBound);
		else if(value > topTrap1 && value <= topTrap2)
			result = maxY;
		else if(value > topTrap2 && value < upperBound)
			result = maxY*(upperBound - value)/(upperBound - topTrap2);
		return result;
	}
	
	@Override
	public String toString() {
		return "Lower bound = "+lowerBound+", Ceil: "+topTrap1+" to "+topTrap2+", Upper bound: "+upperBound;
	}

	public double getLowerBound() {
		return lowerBound;
	}

	public void setLowerBound(double lowerBound) {
		this.lowerBound = lowerBound;
	}

	public double getUpperBound() {
		return upperBound;
	}

	public void setUpperBound(double upperBound) {
		this.upperBound = upperBound;
	}

	public double getTopTrap1() {
		return topTrap1;
	}

	public void setTopTrap1(double topTrap1) {
		this.topTrap1 = topTrap1;
	}

	public double getTopTrap2() {
		return topTrap2;
	}

	public void setTopTrap2(double topTrap2) {
		this.topTrap2 = topTrap2;
	}

	public double getMaxY() {
		return maxY;
	}

	public void setMaxY(double maxY) {
		this.maxY = maxY;
	}

}
