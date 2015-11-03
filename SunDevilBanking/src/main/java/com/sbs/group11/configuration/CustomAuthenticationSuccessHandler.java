package com.sbs.group11.configuration;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class CustomAuthenticationSuccessHandler implements
		AuthenticationSuccessHandler {
	final static Logger logger = Logger
			.getLogger(CustomAuthenticationSuccessHandler.class);

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException {
		handle(request, response, authentication);
		clearAuthenticationAttributes(request);
	}

	protected void handle(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException {
		String targetUrl = determineTargetUrl(authentication);

		if (response.isCommitted()) {
			logger.debug("Response has already been committed. Unable to redirect to "
					+ targetUrl);
			return;
		}

		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	/** Builds the target URL according to the logic defined in the main class. */
	protected String determineTargetUrl(Authentication authentication) {
		Collection<? extends GrantedAuthority> authorities = authentication
				.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			
			// customer
			if (grantedAuthority.getAuthority().equalsIgnoreCase("ROLE_CUSTOMER") || 
					grantedAuthority.getAuthority().equalsIgnoreCase("ROLE_MERCHANT")) {
				return "/home/";
			} 
			
			// Manager
			if (grantedAuthority.getAuthority().equalsIgnoreCase("ROLE_REGULAR")) {
				return "/regular/int-employee-home";
			}
			
			// Admin
			if (grantedAuthority.getAuthority().equalsIgnoreCase("ROLE_ADMIN")) {
				return "/admin/sysadmin-home";
			}
			
			// Manager
			if (grantedAuthority.getAuthority().equalsIgnoreCase("ROLE_MANAGER")) {
				return "/manager/manager-home";
			}
			
			// Manager
			if (grantedAuthority.getAuthority().equalsIgnoreCase("ROLE_GOVERNMENT")) {
				return "/government/government-home";
			}
		}
		
		throw new IllegalStateException();
	}

	protected void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	protected RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}

}
