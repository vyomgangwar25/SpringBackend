package com.ics.zoo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:security.properties")
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

  private long jwtExpire;
}