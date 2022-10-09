package com.nordea.assignment.client;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.USER_AGENT, clientIdentifier);
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.GET, request, responseType);
        return responseEntity.getBody();
    }
}
