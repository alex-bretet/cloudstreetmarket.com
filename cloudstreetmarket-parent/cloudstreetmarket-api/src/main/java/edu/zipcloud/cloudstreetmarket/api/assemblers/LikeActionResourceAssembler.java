package edu.zipcloud.cloudstreetmarket.api.assemblers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import edu.zipcloud.cloudstreetmarket.api.controllers.LikeActionController;
import edu.zipcloud.cloudstreetmarket.api.resources.LikeActionResource;
import edu.zipcloud.cloudstreetmarket.core.entities.LikeAction;

@Component
public class LikeActionResourceAssembler extends ResourceAssemblerSupport<LikeAction, LikeActionResource> {
	
	@Autowired
	EntityLinks entityLinks;

	public LikeActionResourceAssembler() {
	    super(LikeActionController.class, LikeActionResource.class);
	}
	
	@Override
	public LikeActionResource toResource(LikeAction likeAction) {
		LikeActionResource resource = createResourceWithId(likeAction.getId(), likeAction);
		return resource;
	}
	
	protected LikeActionResource instantiateResource(LikeAction entity) {
		return new LikeActionResource(entity);
	}
}