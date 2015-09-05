package edu.zipcloud.cloudstreetmarket.api.resources;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import edu.zipcloud.cloudstreetmarket.core.entities.LikeAction;

@XStreamAlias("resource")
public class LikeActionResource extends Resource<LikeAction> {

	public static final String ACTIONS_PATH = "/actions";
	public static final String LIKES_PATH = "/likes";
	public static final String LIKES = "likes";
	public static final String LIKE = "like";

	public LikeActionResource(LikeAction content, Link... links) {
		super(content, links);
	}
}