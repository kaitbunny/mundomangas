package net.mundomangas.backend.infra.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
	
	@Autowired
	SecurityFilter securityFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		System.out.println("SecurityFilterChain");
		return httpSecurity
				.csrf(csrf -> csrf.disable())
				.sessionManagement(session ->
				 	session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(HttpMethod.POST, "/auth/login", "/auth/user-register").permitAll()
						.requestMatchers(HttpMethod.POST, "/categorias", "/editoras", "/produtos", "/auth/admin-register").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/categorias/*", "/editoras/*", "/produtos/*").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/categorias/*", "/editoras/*", "/produtos/*").hasRole("ADMIN")
						.anyRequest().permitAll()
				)
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		System.out.println("AuthenticationManager");
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		System.out.println("PasswordEncoder");
		return new BCryptPasswordEncoder();
	}
}