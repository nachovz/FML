package ve.com.fml.model.fuzzy;

import java.util.Map;

public class FuzzyVariable {

	private Map<String,FuzzyMembership> fuzzySets;
	
	public double fuzzyEvalSet(double value, String label){
		return fuzzySets.get(label).fuzzyEval(value);
	}
}
