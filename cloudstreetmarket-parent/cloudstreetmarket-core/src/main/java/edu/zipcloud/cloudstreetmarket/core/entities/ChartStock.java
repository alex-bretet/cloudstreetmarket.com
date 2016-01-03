package edu.zipcloud.cloudstreetmarket.core.entities;

import static edu.zipcloud.cloudstreetmarket.core.entities.ChartStock.DISCR;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.social.yahoo.module.ChartHistoMovingAverage;
import org.springframework.social.yahoo.module.ChartHistoSize;
import org.springframework.social.yahoo.module.ChartHistoTimeSpan;
import org.springframework.social.yahoo.module.ChartType;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

import edu.zipcloud.cloudstreetmarket.core.converters.IdentifiableSerializer;
import edu.zipcloud.cloudstreetmarket.core.converters.IdentifiableToIdConverter;

@Entity
@DiscriminatorValue(DISCR)
@XStreamAlias("chart_stock")
public class ChartStock extends Chart {

	public static final String DISCR = "stk";
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stock_code")
	@JsonSerialize(using=IdentifiableSerializer.class)
	@JsonProperty("stockId")
	@XStreamConverter(value=IdentifiableToIdConverter.class, strings={"id"})
	@XStreamAlias("stockId")
	private StockProduct stock;

	public StockProduct getStock() {
		return stock;
	}

	public void setStock(StockProduct stock) {
		this.stock = stock;
	}
	
	public ChartStock(){
	}

	public ChartStock(StockProduct stock, ChartType type, ChartHistoSize histoSize, ChartHistoMovingAverage histoAverage, 
						ChartHistoTimeSpan histoPeriod, Integer intradayWidth, Integer intradayHeight, String path) {
		super(type, histoSize, histoAverage, histoPeriod, intradayWidth, intradayHeight, path);
		this.stock= stock;
	}

	@Override
	public String toString() {
		return "ChartStock [id=" + id + ", getType()=" + getType()
				+ ", getHistoTimeSpan()=" + getHistoTimeSpan()
				+ ", getHistoMovingAverage()=" + getHistoMovingAverage()
				+ ", getHistoSize()=" + getHistoSize()
				+ ", getIntradayWidth()=" + getIntradayWidth()
				+ ", getIntradayHeight()=" + getIntradayHeight()
				+ ", getInternalPath()=" + getInternalPath()
				+ ", getLastUpdate()=" + getLastUpdate() + ", getPath()="
				+ getPath() + "]";
	}
}
