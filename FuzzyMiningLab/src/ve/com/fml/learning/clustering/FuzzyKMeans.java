package ve.com.fml.learning.clustering;

import ve.com.fml.model.fuzzy.FuzzyInstances;
import ve.com.fml.model.fuzzy.distance.FuzzyDistance;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;



public class FuzzyKMeans extends SimpleKMeans {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	FuzzyDistance fd;
	
	@Override
	public String getRevision() {
		// TODO Auto-generated method stub
		return "Test Revision";
	}
	
	@Override
	public void buildClusterer(Instances arg0) throws Exception {
		fd = new FuzzyDistance((FuzzyInstances) arg0);
		this.setDistanceFunction(fd);
		super.buildClusterer(arg0);
	}

}
