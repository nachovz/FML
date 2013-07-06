package ve.com.fml.model.datasource;

import ve.com.fml.model.fuzzy.FuzzyInstances;

public class GlobalData {

	private static GlobalData globalData;
	private FuzzyInstances fuzzyInstances;
	
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
}
