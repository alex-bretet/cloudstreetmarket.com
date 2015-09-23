package edu.zipcloud.cloudstreetmarket.shared.services;

import static edu.zipcloud.cloudstreetmarket.core.i18n.I18nKeys.I18N_INDICES_INDEX_NOT_FOUND;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.social.yahoo.module.ChartHistoMovingAverage;
import org.springframework.social.yahoo.module.ChartHistoSize;
import org.springframework.social.yahoo.module.ChartHistoTimeSpan;
import org.springframework.social.yahoo.module.ChartType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zipcloud.cloudstreetmarket.core.daos.ChartIndexRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.ExchangeRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.IndexRepository;
import edu.zipcloud.cloudstreetmarket.core.daos.MarketRepository;
import edu.zipcloud.cloudstreetmarket.core.entities.ChartIndex;
import edu.zipcloud.cloudstreetmarket.core.entities.Index;
import edu.zipcloud.cloudstreetmarket.core.enums.MarketId;
import edu.zipcloud.cloudstreetmarket.core.services.ResourceBundleService;
import edu.zipcloud.cloudstreetmarket.core.services.SocialUserService;
import edu.zipcloud.cloudstreetmarket.core.specifications.ChartSpecifications;

@Service
@Transactional(readOnly = true)
public class IndexServiceImpl implements IndexService {
	
	private static final Logger log = Logger.getLogger(IndexServiceImpl.class);

	@Autowired
	private MarketRepository marketRepository;
	
	@Autowired
	private ExchangeRepository exchangeRepository;

	@Autowired
	protected IndexRepository indexRepository;

	@Autowired
	protected ChartIndexRepository chartIndexRepository;
	
	@Autowired
	protected SocialUserService usersConnectionRepository;
	
    @Autowired
	public Environment env;

	@Autowired
	protected ResourceBundleService bundle;
    
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
			throw new ResourceNotFoundException(bundle.getFormatted(I18N_INDICES_INDEX_NOT_FOUND, id));
		}
		return index;
	}

	@Override
	public ChartIndex getChartIndex(Index index, ChartType type,
			ChartHistoSize histoSize, ChartHistoMovingAverage histoAverage,
			ChartHistoTimeSpan histoPeriod, Integer intradayWidth,
			Integer intradayHeight) {
		
		log.debug("getChartIndex("+index+", "+type+", "+histoSize+", "+histoAverage+", "+histoPeriod+", "+intradayWidth+", "+intradayHeight+")");
		
		Specification<ChartIndex> spec = new ChartSpecifications<ChartIndex>().typeEquals(type);
		
		if(type.equals(ChartType.HISTO)){
			if(histoSize != null){
				spec = Specifications.where(spec).and(new ChartSpecifications<ChartIndex>().sizeEquals(histoSize));
			}
			if(histoAverage != null){
				spec = Specifications.where(spec).and(new ChartSpecifications<ChartIndex>().histoMovingAverageEquals(histoAverage));
			}
			if(histoPeriod != null){
				spec = Specifications.where(spec).and(new ChartSpecifications<ChartIndex>().histoTimeSpanEquals(histoPeriod));
			}
		}
		else{
			if(intradayWidth != null){
				spec = Specifications.where(spec).and(new ChartSpecifications<ChartIndex>().intradayWidthEquals(intradayWidth));
			}
			if(intradayHeight != null){
				spec = Specifications.where(spec).and(new ChartSpecifications<ChartIndex>().intradayHeightEquals(intradayHeight));
			}
		}
		
		spec = Specifications.where(spec).and(new ChartSpecifications<ChartIndex>().indexEquals(index));
		log.debug("DB CALL");

		return chartIndexRepository.findAll(spec).stream().findFirst().orElse(null);
	}
}
