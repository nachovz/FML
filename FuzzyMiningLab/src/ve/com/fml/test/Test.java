package ve.com.fml.test;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// Read all the instances in the file (ARFF, CSV, XRFF, ...)
		try {
			DataSource source = new DataSource("C:\\Users\\Hdz\\Desktop\\weather.arff");
			Instances instances = source.getDataSet();

			// Make the last attribute be the class
			instances.setClassIndex(instances.numAttributes() - 1);
			
			// Print header and instances.
			System.out.println("\nDataset:\n");
			System.out.println(instances);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
