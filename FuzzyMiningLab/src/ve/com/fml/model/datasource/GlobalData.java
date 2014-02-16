package ve.com.fml.model.datasource;

import java.util.Enumeration;
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
		String evalSummary, confusionMatrix;
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
				System.out.println(GlobalData.getInstance().getFuzzyInstances().getFuzzifiedInstances());
				fdt.buildClassifier(GlobalData.getInstance().getFuzzyInstances().getFuzzifiedInstances());
				Evaluation eval = new Evaluation(GlobalData.getInstance().getFuzzyInstances().getFuzzifiedInstances());
				eval.crossValidateModel(fdt, GlobalData.getInstance().getFuzzyInstances().getFuzzifiedInstances(), 10, new Random(1));
				evalSummary = formatClassifierEvalSummary(eval, 0);
				confusionMatrix = formatConfussionMatrix(eval, 0);
				setResult(fdt+evalSummary+confusionMatrix);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case FuzzyDataMining.MODEL_FUZZY_KMEANS:
			FuzzyKMeans fkm = new FuzzyKMeans();
			try {
				if(opts.containsKey("k"))
					fkm.setNumClusters(Integer.parseInt(opts.get("k").toString()));
				if(opts.containsKey("class")){
					//String[] kMopts = {"-c "+opts.get("class")};
					String[] kMopts = {"-c last"};
					fkm.setOptions(kMopts);
				}
				fkm.config(GlobalData.getInstance().getFuzzyInstances());
				fkm.buildClusterer(GlobalData.getInstance().getFuzzyInstances());
				ClusterEvaluation eval = new ClusterEvaluation();
				eval.setClusterer(fkm);
				eval.evaluateClusterer(GlobalData.getInstance().getFuzzyInstances());
				setResult(formatClustererEvalSummary(eval, 0)+"\n");
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
				evalSummary = formatClassifierEvalSummary(eval, 0);
				confusionMatrix = formatConfussionMatrix(eval, 0);
				setResult(fknn+evalSummary+confusionMatrix);
				/*System.out.println(fknn);
				System.out.println(eval.toSummaryString());*/
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		}
	}

	public String formatClassifierEvalSummary(Evaluation eval, Integer lang) throws Exception{
		return "\n==== Resultado de Validación Cruzada con 10 pliegues ====\n"
				+"\nInstancias clasificadas correctamente:\t"+(int)eval.correct()+"\t"+Math.round(eval.pctCorrect()*10000)/10000.0d+"%"
				+"\nInstancias clasificadas incorrectamente:\t"+(int)eval.incorrect()+"\t"+Math.round(eval.pctIncorrect()*10000)/10000.0d+"%"
				+"\nError absoluto medio:\t\t"+Math.round(eval.meanAbsoluteError()*10000)/10000.0d
				+"\nRaíz cuadrada del error cuadrático medio:\t"+Math.round(eval.rootMeanSquaredError()*10000)/10000.0d
				+"\nError absoluto relativo:\t\t"+Math.round(eval.relativeAbsoluteError()*10000)/10000.0d
				+"\nRaíz cuadrada del error cuadrático relativo:\t"+Math.round(eval.rootRelativeSquaredError()*10000)/10000.0d
				+"\nNúmero total de Instancias:\t\t"+(int)eval.numInstances()+"\n";
	}

	public String formatClustererEvalSummary(ClusterEvaluation eval, Integer lang) throws Exception{
		return eval.clusterResultsToString()+"\n";
				//+eval.getLogLikelihood()+"\n"
				//+eval.getNumClusters()+"\n"
				//+eval.getClassesToClusters().length+"\n"
				//+eval.getClusterAssignments().length+"\n";
	}

	@SuppressWarnings("unchecked")
	public String formatConfussionMatrix(Evaluation eval, Integer lang) throws Exception{
		String result = "\n==== Matriz de confusión ====\n";
		double[][] matrix = eval.confusionMatrix();
		int numClasses = GlobalData.getInstance().getFuzzyInstances().numClasses();
		Enumeration<String> values = GlobalData.getInstance().getFuzzyInstances().classAttribute().enumerateValues();
		for (int i = 0; i < numClasses; i++) {
			result += (char)('a'+i)+"\t";
		}
		result += "<- Clasificado como\n";

		for (int i = 0; i < numClasses; i++) {
			for (int j = 0; j < numClasses; j++) {
				result += (int)matrix[i][j]+"\t";
			}
			result += "| "+(char)('a'+i)+" = "+values.nextElement()+"\n";
		}

		values = GlobalData.getInstance().getFuzzyInstances().classAttribute().enumerateValues();
		result += "\nClase\tPrecisión\tSensibilidad\n";
		for (int i = 0; i < numClasses; i++) {
			result += values.nextElement()+"\t"+Math.round(eval.precision(i)*10000)/10000.0d+"\t"+Math.round(eval.recall(i)*10000)/10000.0d+"\n";
		}
		return result+"\n";
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
