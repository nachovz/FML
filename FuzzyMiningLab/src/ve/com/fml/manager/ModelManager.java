package ve.com.fml.manager;

import ve.com.fml.learning.association.FuzzyApriori;
import ve.com.fml.learning.classifier.FuzzyDT;
import ve.com.fml.learning.classifier.FuzzyKnn;
import ve.com.fml.learning.clustering.FuzzyKMeans;
import ve.com.fml.model.fuzzy.FuzzyDataMining;
import ve.com.fml.model.fuzzy.FuzzyInstances;
import ve.com.fml.model.to.ModelOptionsTo;
import ve.com.fml.model.to.ModelResultTo;

public class ModelManager {


	public ModelResultTo generateModel(FuzzyInstances fuzzyInstances, ModelOptionsTo modelOptionsTo) throws Exception{
		ModelResultTo modelResultTo = new ModelResultTo();
		switch(modelOptionsTo.getModel()){

		case FuzzyDataMining.MODEL_FUZZY_DT:
			FuzzyDT fdt = new FuzzyDT();
			fdt.buildClassifier(fuzzyInstances);
			modelResultTo.setTextRep(fdt.toString());
			break;

		case FuzzyDataMining.MODEL_FUZZY_KNN:
			FuzzyKnn fKnn= new FuzzyKnn(modelOptionsTo.getK());
			fKnn.buildClassifier(fuzzyInstances);
			modelResultTo.setTextRep(fKnn.toString());
			break;
			
		case FuzzyDataMining.MODEL_FUZZY_APRIORI:
			FuzzyApriori fApriori = new FuzzyApriori();
			fApriori.buildAssociations(fuzzyInstances);
			modelResultTo.setTextRep(fApriori.toString());
			break;

		case FuzzyDataMining.MODEL_FUZZY_KMEANS:
			FuzzyKMeans fKMeans= new FuzzyKMeans(modelOptionsTo.getK());
			fKMeans.buildClusterer(fuzzyInstances);
			modelResultTo.setTextRep(fKMeans.toString());
			break;

		}
		return modelResultTo;
	}

}
