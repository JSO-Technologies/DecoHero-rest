package com.jso.deco.config;

import org.glassfish.jersey.server.ResourceConfig;

import com.jso.deco.MyResource;

public class DHResourceConfig extends ResourceConfig {

    /**
     * Register JAX-RS application components.
     */
    public DHResourceConfig () {
    	register(MyResource.class);
//        register(RequestContextFilter.class);
//        register(JerseyResource.class);
//        register(SpringSingletonResource.class);
//        register(SpringRequestResource.class);
//        register(CustomExceptionMapper.class);
    }
}
