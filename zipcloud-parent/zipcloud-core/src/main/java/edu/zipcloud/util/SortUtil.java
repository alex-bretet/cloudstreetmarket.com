package edu.zipcloud.util;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

public class SortUtil {
	
	public static Sort buildSort(List<String> fields, List<String> directions){
		List<Order> result = new LinkedList<>();
		
		for (int i = 0; i < fields.size(); i++) {
			if(directions.get(i) == null){
				result.add(new Order(fields.get(i)));
			}
			else{
				result.add(new Order(Direction.fromString(directions.get(i)), fields.get(i)));
			}
		}
		return new Sort(result);
	}
}
