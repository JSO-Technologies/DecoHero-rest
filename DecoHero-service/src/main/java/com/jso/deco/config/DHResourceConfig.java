package com.jso.deco.config;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import com.jso.deco.MyResource;
import com.jso.deco.filter.SessionManagerRequestFilter;
import com.jso.deco.filter.SessionManagerResponseFilter;

public class DHResourceConfig extends ResourceConfig {

    /**
     * Register JAX-RS application components.
     */
    public DHResourceConfig () {
    	register(JacksonFeature.class);
    	register(SessionManagerRequestFilter.class);
    	register(SessionManagerResponseFilter.class);
    	register(MyResource.class);
//        register(RequestContextFilter.class);
//        register(JerseyResource.class);
//        register(SpringSingletonResource.class);
//        register(SpringRequestResource.class);
//        register(CustomExceptionMapper.class);
    }
}
