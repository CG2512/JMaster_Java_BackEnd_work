package jmaster.io.demo;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jmaster.io.demo.service.JwtTokenService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter{
	
	@Autowired
	UserDetailsService userDetailService;
	@Autowired
	JwtTokenService jwtTokenService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String bearerToken = request.getHeader("Authorization");
		log.info(bearerToken);
		
		if (bearerToken != null && bearerToken.startsWith("Bearer ")){
			String token=bearerToken.substring(7);
			String username=jwtTokenService.getUsername(token);
			if (username != null) {
				//valid token
				UserDetails userDetails=userDetailService.loadUserByUsername(username);
				
				Authentication authentication=new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
				//security simulate
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		filterChain.doFilter(request, response);
	}
	
	
}
