package ve.com.fml.model.fuzzy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ve.com.fml.model.fuzzy.membership.FuzzyMembership;

public class FuzzyVariable implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String,FuzzyMembership> fuzzySets;
	
	public FuzzyVariable() {
		fuzzySets = new HashMap<String,FuzzyMembership>();
	}
	
	public Map<String, FuzzyMembership> getFuzzySets() {
		return fuzzySets;
	}

	public void setFuzzySets(Map<String, FuzzyMembership> fuzzySets) {
		this.fuzzySets = fuzzySets;
	}

	public String getHighestMembershipSet(double testValue){
		Set<String> sets = fuzzySets.keySet();
		double maxValue = -1, value = -1;
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

	public void addFuzzySet(String label, FuzzyMembership memFunction) {
		fuzzySets.put(label, memFunction);
	}
	
	@Override
	public String toString() {
		return fuzzySets.toString();
	}

	public ArrayList<String> getLabelList() {
		ArrayList<String> labels = new ArrayList<String>();
		labels.addAll(fuzzySets.keySet());
		return labels;
	}

	public void removeFuzzySet(String label) {
		fuzzySets.remove(label);
	}
}
