package ve.com.fml.model.to;

import java.io.Serializable;

public class ModelOptionsTo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private int model;
	private int type;
	private int k;
	private int folds = 10;
	
	public int getModel() {
		return model;
	}
	public void setModel(int model) {
		this.model = model;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getK() {
		return k;
	}
	public void setK(int k) {
		this.k = k;
	}
	public int getFolds() {
		return folds;
	}
	public void setFolds(int folds) {
		this.folds = folds;
	}
	
	

}
