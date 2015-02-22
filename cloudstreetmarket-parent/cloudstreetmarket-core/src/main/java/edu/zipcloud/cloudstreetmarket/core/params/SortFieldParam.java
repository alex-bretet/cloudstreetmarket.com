package edu.zipcloud.cloudstreetmarket.core.params;

import java.util.Arrays;
import java.util.List;

public class SortFieldParam{

	List<String> fields;
	
	public SortFieldParam(String arg){
		this.fields = Arrays.asList(arg.split("#"));
	}

	public List<String> getFields() {
		return fields;
	}
}
