package edu.zipcloud.cloudstreetmarket.api.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.social.yahoo.module.ChartHistoMovingAverage;
import org.springframework.social.yahoo.module.ChartHistoSize;
import org.springframework.social.yahoo.module.ChartHistoTimeSpan;
import org.springframework.social.yahoo.module.ChartType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import edu.zipcloud.cloudstreetmarket.api.services.StockProductServiceOnline;
import edu.zipcloud.cloudstreetmarket.core.entities.ChartStock;
import static edu.zipcloud.cloudstreetmarket.api.resources.ChartResource.*;

@Api(value = "Charts for stocks", description = "Financial charts for stocks") // Swagger annotation
@RestController
@ExposesResourceFor(ChartStock.class)
@RequestMapping(value=CHART_STOCK_PATH)
public class ChartStockController extends CloudstreetApiWCI<ChartStock> {
	
	private static final Logger log = Logger.getLogger(ChartStockController.class);
	
	@Autowired
	private StockProductServiceOnline stockProductService;
	
	@RequestMapping(value="/{ticker:[a-zA-Z0-9.:-]+}{extension:\\.[a-z]+}", method=GET)
	@ApiOperation(value = "Get chart for one stock", notes = "Return a chart from one stock")
	public HttpEntity<byte[]> get(
			@ApiParam(value="StockProduct ID: QACL.HM") @PathVariable("ticker") String ticker,
			@ApiParam(value="Extension: json") @PathVariable(value="extension") String extension,
			@ApiParam(value="Histo chart period: 1y") @RequestParam(value="period", required=false) ChartHistoTimeSpan histoPeriod,
			@ApiParam(value="Moving average line: m50") @RequestParam(value="average", required=false) ChartHistoMovingAverage histoAverage,
			@ApiParam(value="Histo chart size: l/m/s") @RequestParam(value="size", required=false) ChartHistoSize histoSize,
			@ApiParam(value="Chart-type INTRADAY / HISTO") @RequestParam(value="type", defaultValue="INTRADAY", required=false) ChartType type,
			@ApiParam(value="Intraday chart width: 300") @RequestParam(value="width", defaultValue="300", required=false) Integer intradayWidth,
			@ApiParam(value="Intraday chart height: 160") @RequestParam(value="height", defaultValue="160", required=false) Integer intradayHeight,
			HttpServletResponse response) throws IOException{
		
		HttpHeaders headers = new HttpHeaders();
		byte[] bytes = null;
		
		try{
			ChartStock chartStock = stockProductService.gather(ticker, type, histoSize, histoAverage, histoPeriod, intradayWidth, intradayHeight);
			bytes = Files.readAllBytes(Paths.get(chartStock.getPath()));
		}
		catch(ResourceNotFoundException e){
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	    	String pathToYahooPicture = env.getProperty("pictures.yahoo.path").concat(File.separator+"graph_not_found.png");
	    	log.error("Resource not found: "+pathToYahooPicture, e);
			bytes = Files.readAllBytes(Paths.get(pathToYahooPicture));
		}

		return new HttpEntity<>(bytes, headers);
	}
}