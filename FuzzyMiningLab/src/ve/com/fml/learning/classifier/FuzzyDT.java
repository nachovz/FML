package ve.com.fml.learning.classifier;

import ve.com.fml.model.fuzzy.FuzzyInstances;
import weka.classifiers.trees.J48;
import weka.core.Instances;

/**
 * Un árbol de decisión difuso basado en C4.5
 * */
public class FuzzyDT extends J48{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		Instances instances;
	
	
	@Override
	public String getRevision() {
		// TODO Auto-generated method stub
		return "Test revision";
	}

	@Override
	public void buildClassifier(Instances arg0) throws Exception {
		super.buildClassifier(arg0);
	}

	public void config(FuzzyInstances fuzzyInstances) {
		instances = fuzzyInstances.getFuzzifiedInstances();
	}
	
}
