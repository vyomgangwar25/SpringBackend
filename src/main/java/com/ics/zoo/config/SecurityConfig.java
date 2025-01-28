package com.ics.zoo.config;

import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.ics.zoo.ZooApplication;
import com.ics.zoo.audit.AuditorAwareImpl;
import com.ics.zoo.dto.UserDTO;
import com.ics.zoo.entities.User;
import com.ics.zoo.enums.EndPoint;
import com.ics.zoo.filter.ZooFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // Enable @PreAuthorize
@EnableJpaAuditing(modifyOnCreate = false)
public class SecurityConfig {
	/**
	 * password encoder bean
	 * 
	 * @return BCryptPasswordEncoder
	 * @author Vyom Gangwar
	 */
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * model mapper uses 3 strategies for mapping.
	 * 
	 * @STANDARD @LOOSE @STRICT. STANDARD:all destination property token is matched
	 *           and at least one source token property matched.
	 * @author Vyom Gangwar
	 */

	/**
	 * ModelMapper bean
	 * 
	 * @return ModelMapper
	 * @author Vyom Gangwar
	 */
	@Bean
	ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
		return modelMapper;
	}

	/**
	 * customized model mappper. mapped UserDTO object to USER object and skip id to
	 * be mapped.
	 * 
	 */
	@Bean
	ModelMapper skipTokenMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.typeMap(UserDTO.class, User.class).addMappings(mp -> {
			mp.skip(User::setId);
		});
		return modelMapper;
	}

	/**
	 * AuditorAware bean
	 * 
	 * @return AuditorAwareImpl
	 * @author Vyom Gangwar
	 */

	@Bean
	public AuditorAware<String> auditorProvider() {
		return new AuditorAwareImpl();
	}

	/**
	 * corsConfigurationSource bean
	 * 
	 * @return source
	 * @author Vyom Gangwar
	 */

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://zoo.com:3000")); // http://localhost:3000
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	/**
	 * SecurityFilterChain Config {@link ZooApplication}
	 * 
	 * @param http,filter
	 * @return SecurityFilterChain
	 * @throws Exception
	 */
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http, ZooFilter filter) throws Exception {
		HttpSecurity security = http.csrf(csrf -> csrf.disable()).cors(cors -> {
			cors.configurationSource(corsConfigurationSource());
		}).formLogin((form) -> form.disable())
				.authorizeHttpRequests((requests) -> requests.requestMatchers(EndPoint.getEndPointsArray()).permitAll()
						.anyRequest().authenticated())
				.oauth2Login(auth -> auth.defaultSuccessUrl("/signin/setInfo"))
				.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
				.httpBasic(httpBasic -> httpBasic.disable());
		return security.build();
	}

}
