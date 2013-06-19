package ve.com.fml.model.datasource;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.experiment.InstanceQuery;

public class InstancesLoader {

	public static Instances loadFromTextFile(String path){
		Instances instances = null;
		try {
			DataSource source = new DataSource(path);
			instances = source.getDataSet();

			// Make the last attribute be the class
			instances.setClassIndex(instances.numAttributes() - 1);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return instances;
	}
	

	public static Instances loadFromMySQLTable(String ip, String port, String schema, String username, String password, String table){
		return loadFromMySQLTable(ip, port, schema, username, password, table, null);
	}
	
	public static Instances loadFromMySQLTable(String ip, String port, String schema, String username, String password, String table, Integer classIndex){
		Instances instances = null;
		try {
			InstanceQuery query = new InstanceQuery();
			query.setDatabaseURL("jdbc:mysql://"+ip+":"+port+"/"+schema);
			query.setUsername(username);
			query.setPassword(password);
			query.setQuery("SELECT * FROM "+table);
			instances = query.retrieveInstances();

			// Make the last attribute be the class
			instances.setClassIndex(classIndex == null? instances.numAttributes() - 1: classIndex);
			
			// Print header and instances.
			System.out.println("\nDataset:\n");
			System.out.println(instances);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return instances;
	}
	
}
