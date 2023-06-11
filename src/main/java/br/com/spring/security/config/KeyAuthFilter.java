package br.com.spring.security.config;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import jakarta.servlet.http.HttpServletRequest;

public class KeyAuthFilter  extends AbstractPreAuthenticatedProcessingFilter{
	private final String headerName;
	
	 public KeyAuthFilter(final String headerName) {
	        this.headerName = headerName;
	    }

	 @Override
	    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
	        return request.getHeader(headerName);
	    }

	    @Override
	    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
	        // No creds when using API key
	        return null;
	    }
	
}
