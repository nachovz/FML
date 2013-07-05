package ve.com.fml.model.fuzzy.membership;

import java.io.Serializable;

public abstract class FuzzyMembership implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public abstract double fuzzyEval(double value);	//funcion de pertenencia
}
