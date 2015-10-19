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
@DiscriminatorValue(CommentAction.DISCR)
@XStreamAlias("comment_event")
public class CommentAction extends SocialEventAction{

	private static final long serialVersionUID = -3584000527863203381L;

	public static final String DISCR = "comment";
	
	protected String comment;

	public CommentAction(){
		super();
	}
	
	public CommentAction(User user, Action targetAction, String comment) {
		super();
		setUser(user);
		setType(UserActivityType.COMMENT);
		setTargetAction(targetAction);
		setDate(new Date());
		setComment(comment);
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public static class Builder extends CommentAction {

		private static final long serialVersionUID = -7449245034377241127L;

		public Builder withComment(String comment) {
        	this.comment = comment;
            return this;
        }
        
        public Builder withUser(User user) {
        	this.user = user;
            return this;
        }
        
        public Builder withTargetAction(Action targetAction) {
        	this.targetAction = targetAction;
            return this;
        }

        public CommentAction build() {
            return new CommentAction(user, targetAction, comment);
        }
    }
}
