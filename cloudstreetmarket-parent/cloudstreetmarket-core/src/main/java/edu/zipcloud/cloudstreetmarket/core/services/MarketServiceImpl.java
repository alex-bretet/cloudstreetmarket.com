package edu.zipcloud.cloudstreetmarket.core.services;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import edu.zipcloud.cloudstreetmarket.core.daos.HistoricalIndexRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.IndexRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.MarketRepository;
import edu.zipcloud.cloudstreetmarket.core.dtos.HistoProductDTO;
import edu.zipcloud.cloudstreetmarket.core.dtos.IndexOverviewDTO;
import edu.zipcloud.cloudstreetmarket.core.entities.HistoricalIndex;
import edu.zipcloud.cloudstreetmarket.core.entities.Index;
import edu.zipcloud.cloudstreetmarket.core.entities.Market;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketCode;
import edu.zipcloud.cloudstreetmarket.core.enums.QuotesInterval;

@Service(value="marketServiceImpl")
public class MarketServiceImpl implements IMarketService {

	private static final DateFormat dateFormat = new SimpleDateFormat("HH:mm");
	
	@Autowired
	private MarketRepository marketRepository;
	
	@Autowired
	private HistoricalIndexRepository historicalIndexRepository;
	
	@Autowired
	private IndexRepository indexRepository;
	
	@Override
	public HistoProductDTO getHistoIndex(String code, MarketCode market, Date fromDate, Date toDate, QuotesInterval interval){
		Map<String, BigDecimal> map = new LinkedHashMap<>();
		Iterator<HistoricalIndex> histoList = null;
		if(fromDate==null){
			 histoList = historicalIndexRepository.findLastIntraDay(code).iterator();
		}
		else{
			 histoList = historicalIndexRepository.findHistoric(code, market, fromDate, toDate, interval).iterator();
		}

		Date firstDate = null;
		HistoricalIndex lastValue = null;
		while(histoList.hasNext()){
			lastValue = histoList.next();
			if(firstDate==null){
				firstDate = lastValue.getFromDate();
			}
			map.put(dateFormat.format(lastValue.getToDate()), lastValue.getClose());
		}

		return (lastValue != null) ? new HistoProductDTO(lastValue.getIndex().getName(), code, map, firstDate, lastValue.getToDate()) : null;
	}

	@Override
	public Page<IndexOverviewDTO> getLastDayIndicesOverview(MarketCode market, Pageable pageable) {
		Market marketEntity = marketRepository.findByCode(market);
		Page<Index> indices = indexRepository.findByMarket(marketEntity, pageable);

		List<IndexOverviewDTO> result = new LinkedList<>();
		for (Index index : indices) {
			result.add(IndexOverviewDTO.build(index));
		}

		return new PageImpl<>(result, pageable, indices.getTotalElements());
	}

	@Override
	public Page<IndexOverviewDTO> getLastDayIndicesOverview(Pageable pageable) {
		Page<Index> indices = indexRepository.findAll(pageable);
		List<IndexOverviewDTO> result = new LinkedList<>();
		for (Index index : indices) {
			result.add(IndexOverviewDTO.build(index));
		}
		return new PageImpl<>(result, pageable, indices.getTotalElements());
	}

}
