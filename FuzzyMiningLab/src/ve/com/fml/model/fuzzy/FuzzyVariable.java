package ve.com.fml.model.fuzzy;

import java.util.Map;
import java.util.Set;

import ve.com.fml.model.fuzzy.membership.FuzzyMembership;

public class FuzzyVariable {

	private Map<String,FuzzyMembership> fuzzySets;
	
	public Map<String, FuzzyMembership> getFuzzySets() {
		return fuzzySets;
	}

	public void setFuzzySets(Map<String, FuzzyMembership> fuzzySets) {
		this.fuzzySets = fuzzySets;
	}

	public String getHighestMembershipSet(double testValue){
		Set<String> sets = fuzzySets.keySet();
		double maxValue = Double.MIN_VALUE, value = Double.MIN_VALUE;
		String highestMembershipSet = null;
		for (String label : sets) {
			value = fuzzyEvalSet(testValue, label);
			if(value > maxValue){
				highestMembershipSet = label;
				maxValue = value; 
			} 
		}
		return highestMembershipSet;
	}
	
	public double fuzzyEvalSet(double value, String label){
		return fuzzySets.get(label).fuzzyEval(value);
	}
}
