package ve.com.fml.model.fuzzy.membership;

public class TriangleFuzzyMembership extends FuzzyMembership {

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
		if(value > lowerBound && value <= topTriangle)
			result = maxY*(value - lowerBound)/(topTriangle - lowerBound);
		else if(value > topTriangle && value <upperBound)
			result = maxY*(upperBound - value)/(upperBound - topTriangle);
		return result;
	}

}
