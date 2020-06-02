package com.projectboard.ppmtool.Security;

import static com.projectboard.ppmtool.Security.SecurityConstants.HEADER_STRING;
import static com.projectboard.ppmtool.Security.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.projectboard.ppmtool.domain.User;
import com.projectboard.ppmtool.services.CustomUserDetailsService;
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JWTTokenProvider tokenProvider;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain chain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String jwt = getJWTFromRequest(httpServletRequest);
			if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
				Long userId = tokenProvider.getUserIdFromJWT(jwt);
				User userDetails = customUserDetailsService.loadUserById(userId);

				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null,Collections.emptyList());
				
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Could not set user authentication in security context",e);
		}
		
		chain.doFilter(httpServletRequest, httpServletResponse);
	}
	
	private String getJWTFromRequest(HttpServletRequest httpServletRequest) {
		String bearerToken = httpServletRequest.getHeader(HEADER_STRING);
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
			return bearerToken.substring(7);
		}
		return null;
	}

	
	
}
