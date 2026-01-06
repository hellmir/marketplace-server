package com.personal.marketnote.product.adapter.out.service.commerce;

import com.personal.marketnote.common.adapter.in.request.RegisterInventoryRequest;
import com.personal.marketnote.common.adapter.out.ServiceAdapter;
import com.personal.marketnote.common.exception.CommerceServiceRequestFailedException;
import com.personal.marketnote.product.port.out.inventory.RegisterInventoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

import static com.personal.marketnote.common.utility.ApiConstant.INTER_SERVER_MAX_REQUEST_COUNT;

@ServiceAdapter
@RequiredArgsConstructor
@Slf4j
public class CommerceServiceClient implements RegisterInventoryPort {
    @Value("${commerce-service.base-url}")
    private String commerceServiceBaseUrl;

    @Value("${spring.jwt.admin-access-token}")
    private String adminAccessToken;

    private final RestTemplate restTemplate;

    @Override
    public void registerInventory(Long pricePolicyId) {
        URI uri = UriComponentsBuilder
                .fromUriString(commerceServiceBaseUrl)
                .path("/api/v1/inventories")
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(adminAccessToken);
        HttpEntity<RegisterInventoryRequest> httpEntity
                = new HttpEntity<>(new RegisterInventoryRequest(pricePolicyId), headers);

        sendRequest(uri, httpEntity, pricePolicyId);
    }

    public void sendRequest(URI uri, HttpEntity<RegisterInventoryRequest> httpEntity, Long pricePolicyId) {
        for (int i = 0; i < INTER_SERVER_MAX_REQUEST_COUNT; i++) {
            try {
                restTemplate.postForEntity(uri, httpEntity, Void.class);
                return;
            } catch (Exception e) {
                log.warn(e.getMessage(), e);

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        log.error("Failed to register inventory: {}", pricePolicyId);
        throw new CommerceServiceRequestFailedException(new IOException());
    }
}
