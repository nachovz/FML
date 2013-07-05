package ve.com.fml.model.fuzzy.distance;

import java.util.Map;

import ve.com.fml.model.fuzzy.FuzzyInstances;
import ve.com.fml.model.fuzzy.FuzzyVariable;
import ve.com.fml.model.fuzzy.membership.FuzzyMembership;
import weka.core.EuclideanDistance;


public class FuzzyDistance extends EuclideanDistance{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FuzzyInstances instances;

	public FuzzyDistance(FuzzyInstances instances){
		super(instances);
		this.instances = instances;
	}

	@Override
	protected double difference(int index, double val1, double val2) {
		if(instances.getMembership().containsKey(index)){
			//calculo la diferencia difusa basado en paper de knn 
			FuzzyVariable fV = instances.getMembership().get(index);
			Map<String,FuzzyMembership> fuzzySets = fV.getFuzzySets();
			double maxVal = 0;
			for (String label : fuzzySets.keySet()) {
				/*double val = (!m_DontNormalize) ? 
						Math.min(norm(fuzzySets.get(label).fuzzyEval(val1), index), norm(fuzzySets.get(label).fuzzyEval(val2), index)):
						Math.min(fuzzySets.get(label).fuzzyEval(val1), fuzzySets.get(label).fuzzyEval(val2));*/
				double val = Math.min(fuzzySets.get(label).fuzzyEval(val1), fuzzySets.get(label).fuzzyEval(val2));
				maxVal = Math.max(maxVal, val);
			}
			//System.out.println("dF("+val1+","+val2+") = "+maxVal+" norm1: "+norm(maxVal,index));
			//return maxVal;
			return (!m_DontNormalize) ? norm(maxVal == 0? 1:1/maxVal,index): maxVal == 0? 1:1/maxVal;
		}else{
			//System.out.println("dC("+val1+","+val2+") = "+super.difference(index, val1, val2));
			return super.difference(index, val1, val2);
		}
	}
}
