package com.personal.marketnote.order.adapter.out.service.cart;

import com.personal.marketnote.common.adapter.out.ServiceAdapter;
import com.personal.marketnote.order.port.out.order.DeleteOrderedCartProductsPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.personal.marketnote.common.utility.ApiConstant.INTER_SERVER_MAX_REQUEST_COUNT;

@ServiceAdapter
@RequiredArgsConstructor
@Slf4j
public class CartServiceClient implements DeleteOrderedCartProductsPort {
    @Value("${product-service.base-url:http://localhost:8081}")
    private String cartServiceBaseUrl;

    @Value("${spring.jwt.admin-access-token}")
    private String adminAccessToken;

    private final RestTemplate restTemplate;

    @Override
    public void delete(List<Long> pricePolicyIds) {
        URI uri = UriComponentsBuilder
                .fromUriString(cartServiceBaseUrl)
                .path("/api/v1/cart/products")
                .queryParam("pricePolicyIds", pricePolicyIds)
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(adminAccessToken);
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        sendRequest(uri, httpEntity, pricePolicyIds);
    }

    public void sendRequest(URI uri, HttpEntity<Void> httpEntity, List<Long> pricePolicyIds) {
        for (int i = 0; i < INTER_SERVER_MAX_REQUEST_COUNT; i++) {
            try {
                restTemplate.exchange(uri, HttpMethod.DELETE, httpEntity, Void.class);
            } catch (Exception e) {
                log.warn(e.getMessage(), e);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        log.error("Failed to delete ordered cart products: {}", pricePolicyIds);
    }
}
