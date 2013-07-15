package ve.com.fml.model.datasource;

import ve.com.fml.model.fuzzy.FuzzyInstances;

public class GlobalData {

	private static GlobalData globalData;
	private FuzzyInstances fuzzyInstances;
	private Integer currentTechnique;
	
	public static GlobalData getInstance(){
		
		if(globalData == null){
			globalData = new GlobalData();
			return globalData;
		}else{
			return globalData;
		}
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
}
