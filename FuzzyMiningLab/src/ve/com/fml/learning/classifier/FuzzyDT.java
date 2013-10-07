package ve.com.fml.learning.classifier;

import weka.classifiers.trees.J48;
import weka.core.Instances;

/**
 * Un �rbol de decisi�n difuso basado en C4.5
 * */
public class FuzzyDT extends J48{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Override
	public String getRevision() {
		// TODO Auto-generated method stub
		return "Test revision";
	}

	@Override
	public void buildClassifier(Instances arg0) throws Exception {
		super.buildClassifier(arg0);
	}

	@Override
	public String toString() {
		return super.toString().replace("J48 pruned tree", "==== Clasificador �rbol de decisi�n difuso ====\n"+
				"\nN�mero m�nimo de hojas = "+getMinNumObj()+"\n"+
				"\nUmbral de confianza para la poda = "+getConfidenceFactor())
				.replace("Number of Leaves", "N�mero de hojas")
				.replace("Size of the tree", "Tama�o del �rbol");
	}

}
