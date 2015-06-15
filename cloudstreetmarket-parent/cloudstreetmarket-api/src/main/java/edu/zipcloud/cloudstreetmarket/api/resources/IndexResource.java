package edu.zipcloud.cloudstreetmarket.api.resources;

import java.io.Serializable;

import org.springframework.hateoas.ResourceSupport;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import edu.zipcloud.cloudstreetmarket.core.entities.Index;

@XStreamAlias("resource")
public class IndexResource extends ResourceSupport implements Serializable{

	public static final String INDEX = "index";
	public static final String INDICES = "indices";
	public static final String INDICES_PATH = "/indices";
	private static final long serialVersionUID = 1L;
	
	private Index index;
	
	public IndexResource() {
	}
	
	public IndexResource(Index index) {
		super();
		this.index = index;
	}

	public Index getIndex() {
		return index;
	}
	
	public void setIndex(Index index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return "IndexResource [index=" + index + "]";
	}
}