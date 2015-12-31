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

import edu.zipcloud.cloudstreetmarket.api.services.IndexService;
import edu.zipcloud.cloudstreetmarket.core.entities.ChartIndex;
import static edu.zipcloud.cloudstreetmarket.api.resources.ChartResource.*;

@Api(value = "Charts for indices", description = "Financial charts for indices") // Swagger annotation
@RestController
@ExposesResourceFor(ChartIndex.class)
@RequestMapping(value=CHART_INDEX_PATH)
public class ChartIndexController extends CloudstreetApiWCI<ChartIndex> {
	
	private static final Logger log = Logger.getLogger(ChartIndexController.class);
	
	@Autowired
	private IndexService indexService;
	
	@RequestMapping(value="/{index:[a-zA-Z0-9^.-]+}{extension:\\.[a-z]+}", method=GET)
	@ApiOperation(value = "Get chart for one index", notes = "Return a chart from one index")
	public HttpEntity<byte[]> get(
			@ApiParam(value="Index ID: ^OEX") @PathVariable("index") String indexId,
			@ApiParam(value="Extension: json") @PathVariable(value="extension") String extension,
			@ApiParam(value="Histo chart period: 1y") @RequestParam(value="period", required=false) ChartHistoTimeSpan histoPeriod,
			@ApiParam(value="Moving average line: m50") @RequestParam(value="average", required=false) ChartHistoMovingAverage histoAverage,
			@ApiParam(value="Histo chart size: l/m/s") @RequestParam(value="size", required=false) ChartHistoSize histoSize,
			@ApiParam(value="Chart-type INTRADAY / HISTO") @RequestParam(value="type", defaultValue="INTRADAY", required=false) ChartType type,
			@ApiParam(value="Intraday chart width: 300") @RequestParam(value="width", defaultValue="300", required=false) Integer intradayWidth,
			@ApiParam(value="Intraday chart height: 160") @RequestParam(value="height", defaultValue="160", required=false) Integer intradayHeight,
			HttpServletResponse response) {
		
		HttpHeaders headers = new HttpHeaders();
		byte[] bytes = null;
		ChartIndex chartIndex = null;
		
		try{
			chartIndex = indexService.gather(indexId, type, histoSize, histoAverage, histoPeriod, intradayWidth, intradayHeight);
			bytes = Files.readAllBytes(Paths.get(chartIndex.getPath()));
		}
		catch(ResourceNotFoundException e){
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	    	String pathToYahooPicture =  env.getProperty("user.home").concat(env.getProperty("pictures.yahoo.path")).concat(File.separator).concat("graph_not_found.png");
			try {
				bytes = Files.readAllBytes(Paths.get(pathToYahooPicture));
			} catch (IOException ioEx) {
				log.error("Failed to load image: "+pathToYahooPicture, ioEx);
			}
		}
		catch(IOException e){
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			log.error("Failed to load image: "+chartIndex.getPath(), e);
		}

		return new HttpEntity<>(bytes, headers);
	}
	
	/*
	private String buildYahooFallback(String indexId, ChartType type,
			ChartHistoSize histoSize, ChartHistoMovingAverage histoAverage,
			ChartHistoTimeSpan histoPeriod, Integer intradayWidth,
			Integer intradayHeight) {
			
		StringBuilder sb;
		if(type.equals(ChartType.INTRADAY)){
			sb = new StringBuilder(YahooAPIType.FINANCIAL_CHARTS_INTRA.getBaseUrl()+"?s="+indexId);
			if(intradayWidth!=null){
				sb.append("&width=");
				sb.append(intradayWidth);
			}
			if(intradayHeight!=null){
				sb.append("&height=");
				sb.append(intradayHeight);
			}
		}
		else{
			sb = new StringBuilder(YahooAPIType.FINANCIAL_CHARTS_HISTO.getBaseUrl()+"?s="+indexId);
			if(histoSize!=null){
				sb.append("&z=");
				sb.append(histoSize.getTag());
			}
			if(histoAverage!=null){
				sb.append("&p=");
				sb.append(histoAverage.getTag());
			}
			if(histoPeriod!=null){
				sb.append("&t=");
				sb.append(histoPeriod.getTag());
			}
		}

		return sb.toString();
	}
	 */
}