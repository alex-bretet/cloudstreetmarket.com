package edu.zipcloud.cloudstreetmarket.api.resources;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import edu.zipcloud.cloudstreetmarket.core.entities.Index;

@XStreamAlias("resource")
public class IndexResource extends Resource<Index> {
	
	public static final String INDEX = "index";
	public static final String INDICES = "indices";
	public static final String INDICES_PATH = "/indices";

	public IndexResource(Index content, Link... links) {
		super(content, links);
	}
}