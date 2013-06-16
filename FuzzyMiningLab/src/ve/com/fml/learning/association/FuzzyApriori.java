package ve.com.fml.learning.association;

import ve.com.fml.model.fuzzy.FuzzyInstances;
import weka.associations.Apriori;
import weka.core.Instances;

public class FuzzyApriori extends Apriori{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getRevision() {
		return "Test Revision";
	}
	
	@Override
	public void buildAssociations(Instances arg0) throws Exception {
		FuzzyInstances fuzzyInstances = (FuzzyInstances) arg0;
		Instances instances = FuzzyInstances.getFuzzifiedInstances(fuzzyInstances);
		super.buildAssociations(instances);
	}
}
