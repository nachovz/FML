package ve.com.fml.model.fuzzy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.smartcardio.ATR;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.w3c.dom.views.AbstractView;

import ve.com.fml.model.fuzzy.membership.FuzzyMembership;
import weka.core.Attribute;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

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
	
	public void addFuzzySet(Integer attrIndex, String label, FuzzyMembership memFunction){
		if(membership.containsKey(attrIndex)){
			membership.get(attrIndex).addFuzzySet(label, memFunction);
		}else{
			FuzzyVariable fV = new FuzzyVariable();
			fV.addFuzzySet(label, memFunction);
			membership.put(attrIndex, fV);
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
		String output = super.toString()+"\n";
		output += "Fuzzy Sets: \n";
		for (Integer index : membership.keySet()) 
			output += attribute(index).name()+": "+membership.get(index)+"\n";
		return output;
	}
	
	public HashMap<Integer, String> getNormalizableAttributes(){
		HashMap<Integer, String> attributes = new HashMap<Integer, String>();
		
		for (int i = 0; i < numAttributes(); i++) {
			if (attribute(i).type() == Attribute.NUMERIC) {
				attributes.put(i,attribute(i).name());
			}
		}
		
		return attributes;
	}

}
