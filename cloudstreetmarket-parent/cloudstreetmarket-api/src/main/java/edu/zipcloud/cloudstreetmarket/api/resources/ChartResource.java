package edu.zipcloud.cloudstreetmarket.api.resources;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import edu.zipcloud.cloudstreetmarket.core.entities.Chart;

@XStreamAlias("resource")
public class ChartResource  extends Resource<Chart> {

	public static final String CHART = "chart";
	public static final String CHART_PATH = "/charts";
	public static final String CHART_INDEX_PATH = "/charts/indices";
	public static final String CHART_STOCK_PATH = "/charts/stocks";
	
	public ChartResource(Chart content, Link... links) {
		super(content, links);
	}
}