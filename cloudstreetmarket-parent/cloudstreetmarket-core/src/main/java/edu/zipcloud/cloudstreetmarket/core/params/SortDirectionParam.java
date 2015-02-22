package edu.zipcloud.cloudstreetmarket.core.params;

import java.util.Arrays;
import java.util.List;

public class SortDirectionParam{

	List<String> directions;
	
	public SortDirectionParam(String arg){
		this.directions = Arrays.asList(arg.split("#"));
	}

	public List<String> getDirections() {
		return directions;
	}
}
