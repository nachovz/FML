package ve.com.fml.test;

import java.util.Random;

import ve.com.fml.learning.association.FuzzyApriori;
import ve.com.fml.learning.classifier.FuzzyDT;
import ve.com.fml.learning.classifier.FuzzyKnn;
import ve.com.fml.learning.clustering.FuzzyKMeans;
import ve.com.fml.model.datasource.InstancesLoader;
import ve.com.fml.model.fuzzy.FuzzyInstances;
import ve.com.fml.model.fuzzy.membership.TriangleFuzzyMembership;
import weka.classifiers.Evaluation;
import weka.clusterers.ClusterEvaluation;
import weka.core.Instances;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		
		
		//cargo data desde archivo con InstancesLoader
		FuzzyInstances fuzzyInstances = new FuzzyInstances(InstancesLoader.loadFromTextFile("C:\\Users\\Hdz\\Desktop\\iris.arff"));

		//preprocesamiento bla bla



		/**
		 * PRUEBAS FuzzyMeans
		 * */

		FuzzyKMeans fkm = new FuzzyKMeans(3);
		try {

			fkm.config(fuzzyInstances);
			ClusterEvaluation eval = new ClusterEvaluation();

			fkm.buildClusterer(fuzzyInstances);
			
			
			
			System.out.println(fkm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		fuzzyInstances.setClassIndex(fuzzyInstances.numAttributes() - 1);

		//agrego conjuntos difusos
		fuzzyInstances.addFuzzySet(2, "Small", new TriangleFuzzyMembership(0, 6, 0));
		fuzzyInstances.addFuzzySet(2, "Large", new TriangleFuzzyMembership(0, 6, 6));

		fuzzyInstances.addFuzzySet(3, "Small", new TriangleFuzzyMembership(0.1, 2.5, 0.1));
		fuzzyInstances.addFuzzySet(3, "Large", new TriangleFuzzyMembership(0.1, 2.5, 2.5));

		//System.out.println(fuzzyInstances);

		/**
		 * PRUEBAS FuzzyKnn
		 * */

		FuzzyKnn knn = new FuzzyKnn(3);
		try {
			knn.config(fuzzyInstances);
			Evaluation eval = new Evaluation(fuzzyInstances);
			eval.crossValidateModel(knn, fuzzyInstances, 10, new Random(1));
			System.out.println(eval.toSummaryString());

			knn.buildClassifier(fuzzyInstances);
			System.out.println(fuzzyInstances.classAttribute().value((int) knn.classifyInstance(fuzzyInstances.instance(0))));
			System.out.println(knn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/**
		 * PRUEBAS FuzzyDT
		 * */

		FuzzyDT dt = new FuzzyDT();
		try {
			Instances instances = fuzzyInstances.getFuzzifiedInstances();

			//habría que implementar una evaluación que tome a las reglas como difusas o esta estimación es buena?
			Evaluation eval = new Evaluation(instances);
			eval.crossValidateModel(dt, instances, 10, new Random(1));
			System.out.println(eval.toSummaryString());

			dt.buildClassifier(instances);
			System.out.println(fuzzyInstances.classAttribute().value((int) knn.classifyInstance(fuzzyInstances.instance(0))));
			System.out.println(dt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/**
		 * PRUEBAS FuzzyApriori
		 * */

		FuzzyApriori fap = new FuzzyApriori();
		try {
			Instances instances = fuzzyInstances.getFuzzifiedInstances();

			fap.buildAssociations(instances);
			System.out.println(fap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}




	}

}
