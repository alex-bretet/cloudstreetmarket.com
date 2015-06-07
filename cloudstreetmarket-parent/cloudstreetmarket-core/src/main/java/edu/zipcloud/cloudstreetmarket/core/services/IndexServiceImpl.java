package edu.zipcloud.cloudstreetmarket.core.services;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import edu.zipcloud.cloudstreetmarket.core.daos.ExchangeRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.HistoricalIndexRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.IndexRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.MarketRepository;
import edu.zipcloud.cloudstreetmarket.core.entities.HistoricalIndex;
import edu.zipcloud.cloudstreetmarket.core.entities.Index;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketId;
import edu.zipcloud.cloudstreetmarket.core.enums.QuotesInterval;

@Service
public class IndexServiceImpl implements IndexService {

	@Autowired
	private MarketRepository marketRepository;
	
	@Autowired
	private ExchangeRepository exchangeRepository;
	
	@Autowired
	private HistoricalIndexRepository historicalIndexRepository;
	
	@Autowired
	private IndexRepository indexRepository;
	
	@Override
	public Set<HistoricalIndex> getHistoricalIndexData(String id, Date fromDate, Date toDate, QuotesInterval interval){
		//Map<String, BigDecimal> map = new LinkedHashMap<>();
		Set<HistoricalIndex> histoSet = new LinkedHashSet<>();
		if(fromDate==null){
			histoSet.addAll(historicalIndexRepository.findOnLastIntraDay(id).stream().collect(Collectors.toSet()));
		}
		else{
			histoSet.addAll(historicalIndexRepository.findSelection(id, fromDate, toDate, interval));
		}
		
		return histoSet;
		
		/*
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
		 */
	}

	@Override
	public Page<Index> getIndices(String exchangeId, MarketId marketId, Pageable pageable) {
		if(!StringUtils.isEmpty(exchangeId)){
			return indexRepository.findByExchange(exchangeRepository.findOne(exchangeId), pageable);
		}
		else if(marketId!=null){
			return indexRepository.findByMarket(marketRepository.findOne(marketId), pageable);
		}
		return indexRepository.findAll(pageable);
	}

	@Override
	public Page<Index> getIndices(Pageable pageable) {
		return indexRepository.findAll(pageable);
	}

	@Override
	public Index getIndex(String id) {
		Index index = indexRepository.findOne(id);
		if(index == null){
			throw new ResourceNotFoundException("No index has been found for the provided index ID: "+id);
		}
		return index;
	}
}
