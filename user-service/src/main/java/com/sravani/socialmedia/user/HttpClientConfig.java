package com.sravani.socialmedia.user;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Kept out of the application class so web-slice tests (@WebMvcTest) don't have to build it.
 */
@Configuration
public class HttpClientConfig {

    /**
     * Resolves http://post-service style URLs through Eureka with client-side load balancing.
     * Explicit timeouts make a slow dependency count as a failure for the circuit breaker
     * instead of tying up request threads indefinitely.
     */
    @Bean
    @LoadBalanced
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .connectTimeout(Duration.ofSeconds(2))
                .readTimeout(Duration.ofSeconds(2))
                .build();
    }
}
