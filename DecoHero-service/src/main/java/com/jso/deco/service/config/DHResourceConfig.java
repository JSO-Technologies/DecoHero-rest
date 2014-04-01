package com.jso.deco.service.config;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import com.jso.deco.service.filter.SessionManagerRequestFilter;
import com.jso.deco.service.filter.SessionManagerResponseFilter;
import com.jso.deco.service.friends.FriendsService;
import com.jso.deco.service.project.ProjectService;
import com.jso.deco.service.user.UserService;

public class DHResourceConfig extends ResourceConfig {

    /**
     * Register JAX-RS application components.
     */
    public DHResourceConfig () {
    	//jackson
    	register(JacksonFeature.class);
    	
    	//filters
    	register(SessionManagerRequestFilter.class);
    	register(SessionManagerResponseFilter.class);
    	
    	//services
    	register(UserService.class);
    	register(ProjectService.class);
    	register(FriendsService.class);
    }
}
