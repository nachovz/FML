package ve.com.fml.model.datasource;

import java.util.Map;

import ve.com.fml.model.fuzzy.FuzzyInstances;

public class GlobalData {

	private static GlobalData globalData;
	private FuzzyInstances fuzzyInstances;
	private FuzzyInstances oldfuzzyInstances;
	private Integer currentTechnique;
	private Map<String,Object> configuredTechnique;
		
	public static GlobalData getInstance(){
		
		if(globalData == null){
			globalData = new GlobalData();
			return globalData;
		}else{
			return globalData;
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
	
	public Map<String, Object> getConfiguredTechnique() {
		return configuredTechnique;
	}

	public void setConfiguredTechnique(Map<String, Object> configuredTechnique) {
		this.configuredTechnique = configuredTechnique;
	}

}
