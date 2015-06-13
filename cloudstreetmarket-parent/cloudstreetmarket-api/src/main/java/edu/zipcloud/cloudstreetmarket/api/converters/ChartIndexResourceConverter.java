package edu.zipcloud.cloudstreetmarket.api.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.hateoas.EntityLinks;
import org.springframework.stereotype.Component;

import edu.zipcloud.cloudstreetmarket.api.resources.ChartResource;
import edu.zipcloud.cloudstreetmarket.core.entities.ChartIndex;
import static edu.zipcloud.cloudstreetmarket.api.resources.IndexResource.*;

@Component
public class ChartIndexResourceConverter implements Converter<ChartIndex, ChartResource> {
	
	@Autowired
	private EntityLinks entityLinks;
	
	@Override
	public ChartResource convert(ChartIndex historicalIndex) {
		ChartResource resource = new ChartResource(historicalIndex);
		resource.add(entityLinks.linkToSingleResource(historicalIndex));
		resource.add(entityLinks.linkToSingleResource(historicalIndex.getIndex()).withRel(INDEX));
		return resource;
	}
}