package edu.zipcloud.cloudstreetmarket.api.resources;

import java.io.Serializable;

import org.springframework.hateoas.ResourceSupport;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import edu.zipcloud.cloudstreetmarket.core.entities.Chart;

@XStreamAlias("resource")
public class ChartResource extends ResourceSupport implements Serializable{

	public static final String CHART = "chart";
	public static final String CHART_PATH = "/charts";
	
	private static final long serialVersionUID = 1L;

	@XStreamAlias("chart")
	private Chart chart;

	public ChartResource() {
	}
	
	public ChartResource(Chart chart) {
		super();
		this.chart = chart;
	}

	public Chart getHistoricalIndex() {
		return chart;
	}

	public void setHistoricalIndex(Chart chart) {
		this.chart = chart;
	}

	public static ChartResource build(Chart chart){
		return new ChartResource(chart);
	}

	@Override
	public String toString() {
		return "ChartResource [chart=" + chart + "]";
	}
}