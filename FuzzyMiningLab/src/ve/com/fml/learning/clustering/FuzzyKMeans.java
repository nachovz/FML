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
	private FuzzyDistance fd;
	
	public void config(FuzzyInstances fuzzyInstances){
		fd = new FuzzyDistance(fuzzyInstances);
	}
	
	public FuzzyKMeans(){
		super();
	}
	
	public FuzzyKMeans(int k){
		super();
		try {
			setNumClusters(k);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public String getRevision() {
		// TODO Auto-generated method stub
		return "Test Revision";
	}
	
	@Override
	public void buildClusterer(Instances arg0) throws Exception {
		this.setDistanceFunction(fd);
		super.buildClusterer(arg0);
	}
	
	@Override
	public String toString() {
		return super.toString().replace("kMeans\n======", "==== K-Medias Difuso ====\n")
				.replace("Number of iterations", "Número de iteraciones")
				.replace("Within cluster sum of squared errors", "Suma del error cuadrático interno de cada cluster")
				.replace("Missing values globally replaced with mean/mode\n", "")
				.replace("Cluster centroids", "Centroides")
				.replace("Attribute", "Atributo")
				.replace("Full Data", "Conjunto de Datos");
	}

}
