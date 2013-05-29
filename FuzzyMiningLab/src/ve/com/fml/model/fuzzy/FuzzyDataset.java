package ve.com.fml.model.fuzzy;

import java.util.Map;

import net.sf.javaml.core.Dataset;

public class FuzzyDataset {
	private Dataset data;	//conjunto de datos javaml
	private Map<Integer,FuzzyVariable> membership;	//relación entre atributos del conjunto de datos nominales y variables lingüísticas
	
	public FuzzyDataset(Dataset data, Map<Integer,FuzzyVariable> membership) {
		//TODO validations
		setData(data);
		setMembership(membership);
	}

	public Dataset getData() {
		return data;
	}

	public void setData(Dataset data) {
		this.data = data;
	}

	public Map<Integer,FuzzyVariable> getMembership() {
		return membership;
	}

	public void setMembership(Map<Integer,FuzzyVariable> membership) {
		this.membership = membership;
	}
	
}
