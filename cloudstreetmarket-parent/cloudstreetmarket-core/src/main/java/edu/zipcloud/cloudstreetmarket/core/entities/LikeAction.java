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
package edu.zipcloud.cloudstreetmarket.core.entities;

import java.util.Date;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import edu.zipcloud.cloudstreetmarket.core.enums.UserActivityType;

@Entity
@DiscriminatorValue(LikeAction.DISCR)
@XStreamAlias("like_event")
public class LikeAction extends SocialEventAction{

	private static final long serialVersionUID = 8864286913794338818L;
	
	public static final String DISCR = "like";
	
	public LikeAction(){
	}
	
	public LikeAction(User user, Action targetAction) {
		setUser(user);
		setType(UserActivityType.LIKE);
		setDate(new Date());
		setTargetAction(targetAction);
	}
	

	public static class Builder extends LikeAction {

		private static final long serialVersionUID = -7449245034377241127L;

        public Builder withUser(User user) {
        	this.user = user;
            return this;
        }
        
        public Builder withTargetAction(Action targetAction) {
        	this.targetAction = targetAction;
            return this;
        }

        public LikeAction build() {
            return new LikeAction(user, targetAction);
        }
    }
}
