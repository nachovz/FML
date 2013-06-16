package ve.com.fml.model.fuzzy.membership;

public class SingletonFuzzyMembership extends FuzzyMembership {

	private double x;
	private double epsilon = 0.0001;
	
	public SingletonFuzzyMembership(double value){
		x = value;
	}
	@Override
	public double fuzzyEval(double value) {
		return Math.abs(value - x) > epsilon ? 0:1;
	}
	
	public double getEpsilon() {
		return epsilon;
	}
	
	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
	}

}
