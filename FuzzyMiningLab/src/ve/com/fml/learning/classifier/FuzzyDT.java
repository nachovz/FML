package ve.com.fml.learning.classifier;

import java.util.HashMap;
import java.util.Map;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.weka.WekaClassifier;
import ve.com.fml.model.fuzzy.Fuzzifier;
import ve.com.fml.model.fuzzy.FuzzyDataset;
import weka.classifiers.trees.J48;

/**
 * Un árbol de decisión difuso basado en C4.5
 * */
public class FuzzyDT implements Classifier{

	/**
	 * 
	 */
	private Classifier wrapJ48;
	private static final long serialVersionUID = 1L;

	@Override
	public void buildClassifier(Dataset arg0) {
		// TODO Auto-generated method stub
		//Difusificar El conjunto de datos
		FuzzyDataset fuzzyDS = Fuzzifier.fuzzify(arg0);
		
		//Generar árbol de decisión J48
		J48 j48 = new J48();
		wrapJ48 = new WekaClassifier(j48);
		wrapJ48.buildClassifier(fuzzyDS.getData());
	}
	
	

	@Override
	public Map<Object, Double> classDistribution(Instance arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object classify(Instance arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private class Tree{
		
		private Node root;
		
		public void generateTree(Node root, Dataset ds){
			//obtengo la lista de los atributos disponibles
			//selecciono atributo y particiono
			ds.classes();
		}
	}
	
	private class Node{
		//un nodo representa una partición dentro del árbol
		private int entropy = 1;
		private Dataset part;
		private Map<Condition,Node> children;
				
		public Node(Dataset part){
			this.part = part;
			this.children = new HashMap<Condition,Node>();
		}
		
		public void setChildren(Map<Condition,Node> children){
			this.children = children;
		}
	}
	
	private class Condition{
		public static final int NUMERIC = 1;
		public static final int NOMINAL = 2;
		private int datatype;
		private Double minValue = null;
		private Double maxValue = null;
		private String nomValue = null;
	}

}
