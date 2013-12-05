package com.jso.deco.config;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import com.jso.deco.filter.SessionManagerRequestFilter;
import com.jso.deco.filter.SessionManagerResponseFilter;
import com.jso.deco.user.UserService;

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
    }
}
