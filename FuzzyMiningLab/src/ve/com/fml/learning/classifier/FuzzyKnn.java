package ve.com.fml.learning.classifier;

import ve.com.fml.model.fuzzy.FuzzyInstances;
import ve.com.fml.model.fuzzy.distance.FuzzyDistance;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;

public class FuzzyKnn extends IBk{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	FuzzyDistance fd;
	
	public void config(FuzzyInstances fuzzyInstances){
		fd = new FuzzyDistance(fuzzyInstances);
	}

	public FuzzyKnn(){
		super();
	}
	
	public FuzzyKnn(int k) {
		super(k);
	}
	
	@Override
	public String getRevision() {
		// TODO Auto-generated method stub
		return "Test revision";
	}

	@Override
	public void buildClassifier(Instances arg0) throws Exception {
		this.getNearestNeighbourSearchAlgorithm().setDistanceFunction(fd);
		super.buildClassifier(arg0);
	}

}
