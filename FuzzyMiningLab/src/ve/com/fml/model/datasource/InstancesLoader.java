package ve.com.fml.model.datasource;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Scanner;

import ve.com.fml.model.fuzzy.FuzzyInstances;
import ve.com.fml.model.fuzzy.membership.SingletonFuzzyMembership;
import ve.com.fml.model.fuzzy.membership.TrapFuzzyMembership;
import ve.com.fml.model.fuzzy.membership.TriangleFuzzyMembership;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class InstancesLoader {

	public static FuzzyInstances loadFromArffFile(String path){
		Instances instances = null;
		try {
			DataSource source = new DataSource(path);
			instances = source.getDataSet();


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new FuzzyInstances(instances);
	}
	
	public static FuzzyInstances loadFromFarffFile(String path){
		FuzzyInstances fuzzyInstances = null;
		String currentLine = "", lines = "";
		Instances instances = null;
		boolean fuzzyData = false;
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(path));
			scanner.useDelimiter("\n");
			while(scanner.hasNext()){
				currentLine = scanner.next();
				if(currentLine.equalsIgnoreCase("@fuzzydata")){
					fuzzyData = true;
					break;
				}
				lines+="\n"+currentLine;
			}
			//System.out.println(lines);
			InputStream inputStream = new ByteArrayInputStream(lines.getBytes());
			DataSource source = new DataSource(inputStream);
			instances = source.getDataSet();
			fuzzyInstances = new FuzzyInstances(instances);
			
			if(fuzzyData){
				String[] fuzzySetStrings = null;
				while(scanner.hasNext()){
					fuzzySetStrings = scanner.next().split(" ");
					if(fuzzySetStrings[0].equalsIgnoreCase("@fuzzyset")){
						switch(fuzzySetStrings[3].toLowerCase()){
						case "trap":
							fuzzyInstances.addFuzzySet(Integer.parseInt(fuzzySetStrings[1]), fuzzySetStrings[2], 
									new TrapFuzzyMembership(Double.parseDouble(fuzzySetStrings[4]), Double.parseDouble(fuzzySetStrings[7]),
											Double.parseDouble(fuzzySetStrings[5]), Double.parseDouble(fuzzySetStrings[6])));
							break;
						case "triangle":
							fuzzyInstances.addFuzzySet(Integer.parseInt(fuzzySetStrings[1]), fuzzySetStrings[2], 
									new TriangleFuzzyMembership(Double.parseDouble(fuzzySetStrings[4]), Double.parseDouble(fuzzySetStrings[6]),
											Double.parseDouble(fuzzySetStrings[5])));
							break;
						case "singleton":
							fuzzyInstances.addFuzzySet(Integer.parseInt(fuzzySetStrings[1]), fuzzySetStrings[2], 
									new SingletonFuzzyMembership(Double.parseDouble(fuzzySetStrings[4])));
							break;
						}
						
					}
				}
			}	
			// Make the last attribute be the class
			//instances.setClassIndex(instances.numAttributes() - 1);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(scanner != null)
				scanner.close();
		}
		return fuzzyInstances;
	}

	public static void saveToTextFile(FuzzyInstances instances, String path){

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(path));
			writer.write(instances.toString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}


}
