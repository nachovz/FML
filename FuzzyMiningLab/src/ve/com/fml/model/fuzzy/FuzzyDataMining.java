package ve.com.fml.model.fuzzy;


public class FuzzyDataMining {


	public static final int MODEL_FUZZY_DT = 0;
	public static final int MODEL_FUZZY_KNN = 1;
	public static final int MODEL_FUZZY_APRIORI = 2;
	public static final int MODEL_FUZZY_KMEANS = 3;
	
	public static final String[] names = {"Árbol de decisión","K-vecinos","Apriori","K-medias"};

	public static final int TYPE_CLASSIFICATION = 0;
	public static final int TYPE_REGRESSION = 1;
	public static final int TYPE_ASSOCIATION = 2;
	public static final int TYPE_CLUSTERING = 3;

	public static boolean check(int type, int model){
		boolean result = false;
		switch(type){
			case TYPE_CLASSIFICATION:
				switch(model){
				case MODEL_FUZZY_DT: case MODEL_FUZZY_KNN:
					result = true;
					break;
				}
				break;
			case TYPE_REGRESSION:
				switch(model){
				case MODEL_FUZZY_KNN:
					result = true;
					break;
				}
				break;
			case TYPE_ASSOCIATION:
				switch(model){
				case MODEL_FUZZY_APRIORI:
					result = true;
					break;
				}
				break;
			case TYPE_CLUSTERING:
				switch(model){
				case MODEL_FUZZY_KMEANS:
					result = true;
					break;
				}
				break;
		}
		return result;
	}

	public static String gettechniqueName(int technique){
		return names[technique];
	}
}
