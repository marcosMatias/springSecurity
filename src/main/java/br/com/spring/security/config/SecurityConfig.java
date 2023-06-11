package br.com.spring.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${http.auth-token-header-name}")
    private String principalRequestHeader;

    @Value("${http.auth-token}")
    private String principalRequestValue;
    
    @Value("${http.auth-teste-header-name}")
    private String testeCab;
  
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	KeyAuthFilter filter = new KeyAuthFilter(principalRequestHeader);
    	
    	filter.setAuthenticationManager(new AuthenticationManager() {
			
			@Override
			public Authentication authenticate(Authentication authentication) throws AuthenticationException {
				String principal = (String) authentication.getPrincipal();

				if (!principalRequestValue.equals(principal)) {
					authentication.setAuthenticated(false);
					throw new BadCredentialsException("The API key was not found or not the expected value.");
				}
				
				if(testeCab.equals("teste")) {
					System.out.println("PARANGARICOTIRRIMIRUARO!!!!!!!!!!!!");
				}
				
				authentication.setAuthenticated(true);
				
				
				return authentication;

			}
		});
    	
	
		     http.csrf((csrf) -> csrf.disable())
		     	.httpBasic(b-> b.disable())
		     	.formLogin(f-> f.disable())
		     	.logout(l-> l.disable())
				 .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				 .authorizeHttpRequests(authorize -> authorize.anyRequest()
						                                      .authenticated())
				 .addFilter(filter)
				;
		
		     
		  
		     
				http.requiresChannel(
						req -> req.requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null).requiresSecure());
		     
		    
		     return http.build();
 

         
    }
    
    
    
    
    
    
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web)-> web.ignoring().requestMatchers("/actuator", "/actuator/**","/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**");
    }
    
	
}
