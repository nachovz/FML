package ve.com.fml.model.fuzzy;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import ve.com.fml.model.fuzzy.membership.FuzzyMembership;
import ve.com.fml.model.fuzzy.membership.SingletonFuzzyMembership;
import ve.com.fml.model.fuzzy.membership.TrapFuzzyMembership;
import ve.com.fml.model.fuzzy.membership.TriangleFuzzyMembership;
import weka.core.Attribute;
import weka.core.Instances;

public class FuzzyInstances extends Instances {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<Integer,FuzzyVariable> membership;	//relación entre atributos numéricos del conjunto de datos y variables lingüísticas


	public FuzzyInstances(FuzzyInstances arg0) {
		super(arg0);
		this.membership = new HashMap<Integer, FuzzyVariable>();
		membership.putAll(arg0.getMembership());
	}

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

	public void addFuzzySet(Integer attrIndex, String label, FuzzyMembership memFunction){
		if(membership.containsKey(attrIndex)){
			membership.get(attrIndex).addFuzzySet(label, memFunction);
		}else{
			FuzzyVariable fV = new FuzzyVariable();
			fV.addFuzzySet(label, memFunction);
			membership.put(attrIndex, fV);
		}
	}

	public void removeFuzzySet(Integer attrIndex, String label){
		if(membership.containsKey(attrIndex)){
			membership.get(attrIndex).removeFuzzySet(label);
		}
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
			try{
				Attribute oldAttr = attribute(attrIndex);
				FuzzyVariable fV = fuzzyVars.get(attrIndex); 
				Attribute newAttr = new Attribute(oldAttr.name(), fV.getLabelList());
				fuzzyfied.deleteAttributeAt(attrIndex);
				fuzzyfied.insertAttributeAt(newAttr, attrIndex);
				String newLabel = null;


				for(int i = 0; i < numInstances; i++){
					newLabel = fV.getHighestMembershipSet(instance(i).value(oldAttr));

					fuzzyfied.instance(i).setValue(attrIndex, newLabel);

				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return fuzzyfied;
	}

	@Override
	public String toString() {
		String output = super.toString()+"\n\n@fuzzydata\n";
		String type = null, params = null;

		for (Integer index : membership.keySet()){
			Map<String, FuzzyMembership> fuzzyVars = membership.get(index).getFuzzySets();
			for (String label : fuzzyVars.keySet()) {
				if(fuzzyVars.get(label) instanceof SingletonFuzzyMembership){
					type = "singleton";
					SingletonFuzzyMembership fuzzyMembership = (SingletonFuzzyMembership) fuzzyVars.get(label); 
					params = fuzzyMembership.getX()+"";
				}
				if(fuzzyVars.get(label) instanceof TriangleFuzzyMembership){
					type = "triangle";
					TriangleFuzzyMembership fuzzyMembership = (TriangleFuzzyMembership) fuzzyVars.get(label);
					params = fuzzyMembership.getLowerBound()+" "+fuzzyMembership.getTopTriangle()+" "+fuzzyMembership.getUpperBound();
				}
					
				if(fuzzyVars.get(label) instanceof TrapFuzzyMembership){
					type = "trap";
					TrapFuzzyMembership fuzzyMembership = (TrapFuzzyMembership) fuzzyVars.get(label);
					params = fuzzyMembership.getLowerBound()+" "+fuzzyMembership.getTopTrap1()+" "+fuzzyMembership.getTopTrap2()+" "+fuzzyMembership.getUpperBound();
				}
					
				output += "@fuzzyset "+index+" "+label+" "+type+" "+params+"\n";
			}
			
		}
		return output;
	}

	public HashMap<String, Integer> getNumericAttributes(){
		HashMap<String, Integer> attributes = new HashMap<String, Integer>();

		for (int i = 0; i < numAttributes(); i++) {
			if (attribute(i).type() == Attribute.NUMERIC) {
				attributes.put(attribute(i).name(),i);
			}
		}

		return attributes;
	}
	
	public HashMap<String, Integer> getNominalAttributes(){
		HashMap<String, Integer> attributes = new HashMap<String, Integer>();

		for (int i = 0; i < numAttributes(); i++) {
			if (attribute(i).type() == Attribute.NOMINAL) {
				attributes.put(attribute(i).name(),i);
			}
		}

		return attributes;
	}

	public Vector<String> getFuzzySets(Integer attrIndex){
		Vector<String> fSLabels = new Vector<String>();
		if(membership.containsKey(attrIndex))
			fSLabels.addAll(membership.get(attrIndex).getLabelList());
		return fSLabels;
	}

}
