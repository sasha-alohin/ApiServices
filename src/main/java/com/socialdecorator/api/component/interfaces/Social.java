package com.socialdecorator.api.component.interfaces;

import java.util.List;

import com.socialdecorator.api.component.enteties.enteties_search.ActivitySearch;
import com.socialdecorator.api.component.enteties.enteties_search.CommentSearch;
import com.socialdecorator.api.component.enteties.enteties_search.UserSearch;
import com.socialdecorator.api.component.enteties.enteties_social.Activity;
import com.socialdecorator.api.component.enteties.enteties_social.Comment;
import com.socialdecorator.api.component.enteties.enteties_social.User;

public interface Social {
		
	public List<User> getUser(UserSearch user) throws Exception;
	
	public List<Comment> getComment(CommentSearch comment) throws Exception;
	
	public List<Activity> getActivity(ActivitySearch activity) throws Exception;

}
