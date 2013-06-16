package ve.com.fml.model.fuzzy;

import java.util.HashMap;
import java.util.Map;

import weka.core.Attribute;
import weka.core.Instances;

public class FuzzyInstances extends Instances {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<Integer,FuzzyVariable> membership;	//relación entre atributos numéricos del conjunto de datos y variables lingüísticas
	
	
	public FuzzyInstances(Instances arg0, Map<Integer,FuzzyVariable> membership) {
		super(arg0);
		setMembership(membership);
	}
	
	public FuzzyInstances(Instances arg0) {
		super(arg0);
		membership = new HashMap<Integer, FuzzyVariable>();
	}

	public Map<Integer,FuzzyVariable> getMembership() {
		return membership;
	}

	public void setMembership(Map<Integer,FuzzyVariable> membership) {
		this.membership = membership;
	}
	
	/**
	 * Genera un conjunto de datos difusificado en el cual cada atributo marcado como difuso es reemplazado por
	 * la etiqueta relativa al conjunto difuso para el cual la instancia tiene el mayor grado de pertenencia
	 * */
	public Instances getFuzzifiedInstances(){
		Instances fuzzyfied = new Instances(this);
		Map<Integer,FuzzyVariable> fuzzyVars = getMembership();
		int numInstances = fuzzyfied.numInstances();
		for (Integer attrIndex : fuzzyVars.keySet()) {
			Attribute oldAttr = attribute(attrIndex);
			Attribute newAttr = new Attribute(oldAttr.name(),Attribute.NOMINAL);
			fuzzyfied.insertAttributeAt(newAttr, attrIndex);
			String newLabel = null;
			FuzzyVariable fV = fuzzyVars.get(attrIndex);
			
			for(int i = 0; i < numInstances; i++){
				newLabel = fV.getHighestMembershipSet(instance(i).value(newAttr));
				fuzzyfied.instance(i).setValue(newAttr, newLabel);
			}
		}
		return fuzzyfied;
	}

}
