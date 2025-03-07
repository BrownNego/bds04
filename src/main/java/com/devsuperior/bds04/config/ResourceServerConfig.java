package com.devsuperior.bds04.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private Environment env;
		
	private static final String[] PUBLIC = { "/oauth/token", "/h2-console/**", "/cities/**", "/events/**" };
	
	private static final String[] CLIENT = { "/events/**" };
	
	private static final String[] ADMIN = {"/cities/**" };
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		// H2
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}
		
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET, PUBLIC).permitAll()
		.antMatchers(HttpMethod.POST, CLIENT).hasAnyRole("CLIENT", "ADMIN")
		.antMatchers(HttpMethod.POST, ADMIN).hasAnyRole("ADMIN")
		.anyRequest().hasAnyRole("ADMIN");
	}
	
}
