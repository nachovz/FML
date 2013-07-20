package ve.com.fml.model.fuzzy.membership;

public class TriangleFuzzyMembership extends FuzzyMembership {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double lowerBound;
	private double upperBound;
	private double topTriangle;
	private double maxY = 1;
	
	public TriangleFuzzyMembership(double lower, double upper, double top){
		lowerBound = lower;
		upperBound = upper;
		topTriangle = top;
	}
	
	public TriangleFuzzyMembership(double lower, double upper, double top, double maxY){
		this(lower,upper,top);
		this.maxY = maxY;
	}
	
	@Override
	public double fuzzyEval(double value) {
		double result = 0;
		if(value == topTriangle)
			result = 1;
		else if(value > lowerBound && value < topTriangle)
			result = maxY*(value - lowerBound)/(topTriangle - lowerBound);
		else if(value > topTriangle && value < upperBound)
			result = maxY*(upperBound - value)/(upperBound - topTriangle);
		return result;
	}
	
	@Override
	public String toString() {
		return "Lower bound = "+lowerBound+", Peak: "+topTriangle+", Upper bound: "+upperBound;
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

	public double getTopTriangle() {
		return topTriangle;
	}

	public void setTopTriangle(double topTriangle) {
		this.topTriangle = topTriangle;
	}

	public double getMaxY() {
		return maxY;
	}

	public void setMaxY(double maxY) {
		this.maxY = maxY;
	}


}
