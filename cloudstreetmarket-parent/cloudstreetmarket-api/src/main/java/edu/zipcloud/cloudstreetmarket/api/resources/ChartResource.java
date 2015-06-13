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
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((chart == null) ? 0 : chart.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChartResource other = (ChartResource) obj;
		if (chart == null) {
			if (other.chart != null)
				return false;
		} else if (!chart.equals(other.chart))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ChartResource [chart=" + chart.toString() + "]";
	}
}