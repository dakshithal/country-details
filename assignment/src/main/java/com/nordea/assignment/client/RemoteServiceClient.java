package com.nordea.assignment.client;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
public class RemoteServiceClient {
    private final RestTemplate restTemplate;
    public final String clientIdentifier = "country";

    @CircuitBreaker(name = clientIdentifier)
    @Retry(name = clientIdentifier)
    @Bulkhead(name = clientIdentifier)
    public <T> T getForObject(String url, Class<T> responseType) {
        return restTemplate.getForObject(url, responseType);
    }
}
