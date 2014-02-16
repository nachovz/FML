package ve.com.fml.model.fuzzy.membership;

import org.apache.commons.math3.analysis.function.Gaussian;

public class GaussianFuzzyMembership extends FuzzyMembership{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double sigma;
	private double mu;
	
	public GaussianFuzzyMembership(double sigma, double mu) {
		super();
		this.sigma = sigma;
		this.mu = mu;
	}
	

	@Override
	public double fuzzyEval(double value) {
		Gaussian gaussian = new Gaussian(mu, sigma);
		return gaussian.value(value);
	}
	

	public double getSigma() {
		return sigma;
	}

	public void setSigma(double sigma) {
		this.sigma = sigma;
	}

	public double getMu() {
		return mu;
	}

	public void setMu(double mu) {
		this.mu = mu;
	}


}
