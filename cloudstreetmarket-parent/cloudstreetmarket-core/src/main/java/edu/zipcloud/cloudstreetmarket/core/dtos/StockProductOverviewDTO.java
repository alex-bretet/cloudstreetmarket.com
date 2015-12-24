package edu.zipcloud.cloudstreetmarket.core.dtos;

import java.math.BigDecimal;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import edu.zipcloud.cloudstreetmarket.core.entities.StockProduct;

@XStreamAlias("stock")
public class StockProductOverviewDTO extends ProductOverviewDTO {

	private static final long serialVersionUID = 3681041931966104346L;
	
	public StockProductOverviewDTO(){
		super();
	}
	
	public StockProductOverviewDTO(String name, String code, String market,
			String currency, BigDecimal latestValue, BigDecimal latestChange,
			BigDecimal latestChangePercent, BigDecimal prevClose,
			BigDecimal high, BigDecimal low) {
		super(name, code, market, currency, latestValue, latestChange,
				latestChangePercent, prevClose, high, low);
	}

	public static StockProductOverviewDTO build(StockProduct stock){
		return new StockProductOverviewDTO(
				stock.getName(), 
				stock.getCode(),
				(stock.getMarket()!=null)? stock.getMarket().getName(): null, 
				stock.getCurrency(), 
				stock.getDailyLatestValue(), 
				stock.getDailyLatestChange(), 
				stock.getDailyLatestChangePercent(), 
				stock.getPreviousClose(), 
				stock.getHigh(), 
				stock.getLow());
	}
}