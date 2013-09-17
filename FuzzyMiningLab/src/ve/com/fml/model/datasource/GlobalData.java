package ve.com.fml.model.datasource;

import java.util.HashMap;
import java.util.Random;

import ve.com.fml.learning.association.FuzzyApriori;
import ve.com.fml.learning.classifier.FuzzyDT;
import ve.com.fml.learning.classifier.FuzzyKnn;
import ve.com.fml.learning.clustering.FuzzyKMeans;
import ve.com.fml.model.fuzzy.FuzzyDataMining;
import ve.com.fml.model.fuzzy.FuzzyInstances;
import weka.classifiers.Evaluation;
import weka.clusterers.ClusterEvaluation;

public class GlobalData {

	private static GlobalData globalData;
	private FuzzyInstances fuzzyInstances;
	private FuzzyInstances oldfuzzyInstances;
	private Integer currentTechnique;
	private HashMap<String, Object> configuredTechnique;
	private String result;
	
	private String datasetName;
		
	public static GlobalData getInstance(){
		
		if(globalData == null){
			globalData = new GlobalData();
			return globalData;
		}else{
			return globalData;
		}
	}
	
	public void setupAndRun() {
		int selectedTech = GlobalData.getInstance().getCurrentTechnique();
		HashMap<String, Object> opts = GlobalData.getInstance().getConfiguredTechnique();
		setResult("");
		switch(selectedTech){
		case FuzzyDataMining.MODEL_FUZZY_APRIORI:
			FuzzyApriori fap = new FuzzyApriori();
			if(opts.containsKey("nr"))
				fap.setNumRules(Integer.parseInt(opts.get("nr").toString()));
			if(opts.containsKey("mc"))
				fap.setMinMetric(Double.parseDouble(opts.get("mc").toString()));
			try {
				fap.buildAssociations(GlobalData.getInstance().getFuzzyInstances().getFuzzifiedInstances());
				setResult(fap.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case FuzzyDataMining.MODEL_FUZZY_DT:
			FuzzyDT fdt = new FuzzyDT();
			if(opts.containsKey("mno"))
				fdt.setMinNumObj(Integer.parseInt(opts.get("mno").toString()));
			if(opts.containsKey("ctt"))
				fdt.setConfidenceFactor(Float.parseFloat(opts.get("ctt").toString()));
			try {
				fdt.buildClassifier(GlobalData.getInstance().getFuzzyInstances());
				Evaluation eval = new Evaluation(GlobalData.getInstance().getFuzzyInstances().getFuzzifiedInstances());
				eval.crossValidateModel(fdt, GlobalData.getInstance().getFuzzyInstances().getFuzzifiedInstances(), 10, new Random(1));
				setResult(fdt+"\n"+eval.toSummaryString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case FuzzyDataMining.MODEL_FUZZY_KMEANS:
			FuzzyKMeans fkm = new FuzzyKMeans();
			if(opts.containsKey("k")){
				try {
					fkm.setNumClusters(Integer.parseInt(opts.get("k").toString()));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				fkm.config(GlobalData.getInstance().getFuzzyInstances());
				fkm.buildClusterer(GlobalData.getInstance().getFuzzyInstances());
				ClusterEvaluation eval = new ClusterEvaluation();
				eval.setClusterer(fkm);
				eval.evaluateClusterer(GlobalData.getInstance().getFuzzyInstances());
				setResult(fkm+"\n"+eval.clusterResultsToString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case FuzzyDataMining.MODEL_FUZZY_KNN:
			FuzzyKnn fknn = new FuzzyKnn();
			if(opts.containsKey("k"))
				fknn.setKNN(Integer.parseInt(opts.get("k").toString()));
			try {
				fknn.config(GlobalData.getInstance().getFuzzyInstances());
				fknn.buildClassifier(GlobalData.getInstance().getFuzzyInstances());
				Evaluation eval = new Evaluation(GlobalData.getInstance().getFuzzyInstances());
				eval.crossValidateModel(fknn, GlobalData.getInstance().getFuzzyInstances(), 10, new Random(1));
				setResult(fknn+"\n"+eval.toSummaryString());
				/*System.out.println(fknn);
				System.out.println(eval.toSummaryString());*/
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		}
	}
	
	public static void clearInstance(){
		globalData = null;
	}

	public FuzzyInstances getFuzzyInstances() {
		return fuzzyInstances;
	}

	public void setFuzzyInstances(FuzzyInstances fuzzyInstances) {
		this.fuzzyInstances = fuzzyInstances;
	}

	public Integer getCurrentTechnique() {
		return currentTechnique;
	}

	public void setCurrentTechnique(Integer currentTechnique) {
		this.currentTechnique = currentTechnique;
	}

	public FuzzyInstances getOldfuzzyInstances() {
		return oldfuzzyInstances;
	}

	public void setOldfuzzyInstances(FuzzyInstances oldfuzzyInstances) {
		this.oldfuzzyInstances = oldfuzzyInstances;
	}
	
	public void storeInstancesBackup(){
		oldfuzzyInstances = new FuzzyInstances(fuzzyInstances);
	}
	
	public void restoreInstancesBackup(){
		fuzzyInstances = new FuzzyInstances(oldfuzzyInstances);
	}
	
	public static boolean instanceCreated(){
		return globalData != null;
	}
	
	public HashMap<String, Object> getConfiguredTechnique() {
		return configuredTechnique;
	}

	public void setConfiguredTechnique(HashMap<String, Object> configuredTechnique) {
		this.configuredTechnique = configuredTechnique;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getDatasetName() {
		return datasetName;
	}

	public void setDatasetName(String datasetName) {
		this.datasetName = datasetName;
	}

}
