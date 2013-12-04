package com.jso.deco.test.utils;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.container.ContainerRequestContext;

import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.ExtendedUriInfo;
import org.glassfish.jersey.server.internal.routing.UriRoutingContext;

public class RequestUtils {
	public static ContainerRequestContext getMockedRequestContext(String uri) throws URISyntaxException {
		ExtendedUriInfo uriInfo = mock(UriRoutingContext.class);
		ContainerRequestContext requestContext = mock(ContainerRequest.class);
		when(requestContext.getUriInfo()).thenReturn(uriInfo);
		when(uriInfo.getBaseUri()).thenReturn(new URI(uri));
		return requestContext;
	}
}
