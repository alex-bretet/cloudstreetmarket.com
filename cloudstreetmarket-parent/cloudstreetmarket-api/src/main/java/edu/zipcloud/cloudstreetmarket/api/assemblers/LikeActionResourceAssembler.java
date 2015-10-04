/***
 *  Cloudstreetmarket.com is a Spring MVC showcase application developed 
 *  with the book Spring MVC Cookbook [PACKT] (2015). 
 * 	Copyright (C) 2015  Alex Bretet
 *  
 *  This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 **/
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