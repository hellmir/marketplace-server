package com.personal.marketnote.commerce.adapter.out.service.product;

import com.personal.marketnote.commerce.adapter.out.service.product.dto.ProductCursorResponse;
import com.personal.marketnote.commerce.adapter.out.service.product.dto.ProductItemResponse;
import com.personal.marketnote.commerce.adapter.out.service.product.dto.ProductListResponse;
import com.personal.marketnote.commerce.port.out.product.FindProductByPricePolicyPort;
import com.personal.marketnote.commerce.port.out.result.product.GetOrderedProductResult;
import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.common.adapter.out.ServiceAdapter;
import com.personal.marketnote.common.utility.FormatValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ServiceAdapter
@RequiredArgsConstructor
@Slf4j
public class ProductServiceClient implements FindProductByPricePolicyPort {
    @Value("${product-service.base-url:http://localhost:8081}")
    private String productServiceBaseUrl;

    @Value("${spring.jwt.admin-access-token}")
    private String adminAccessToken;

    private final RestTemplate restTemplate;

    @Override
    public Map<Long, GetOrderedProductResult> findByPricePolicyIds(List<Long> pricePolicyIds) {
        if (!FormatValidator.hasValue(pricePolicyIds)) {
            return Map.of();
        }

        URI uri = UriComponentsBuilder.fromUriString(productServiceBaseUrl)
                .path("/api/v1/products")
                .queryParam("pricePolicyIds", pricePolicyIds)
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(adminAccessToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<BaseResponse<ProductListResponse>> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    request,
                    new ParameterizedTypeReference<>() {
                    }
            );

            BaseResponse<ProductListResponse> baseResponse = response.getBody();
            ProductListResponse content = FormatValidator.hasValue(baseResponse)
                    ? baseResponse.getContent()
                    : null;
            if (!FormatValidator.hasValue(content)) {
                return Map.of();
            }

            ProductCursorResponse<ProductItemResponse> products = content.products();
            List<ProductItemResponse> productItems = FormatValidator.hasValue(products)
                    && FormatValidator.hasValue(products.contents())
                    ? products.contents()
                    : List.of();

            Map<Long, GetOrderedProductResult> result = new HashMap<>();
            for (ProductItemResponse item : productItems) {
                if (!FormatValidator.hasValue(item) || !FormatValidator.hasValue(item.pricePolicy())) {
                    continue;
                }

                Long policyId = item.pricePolicy().id();
                if (!FormatValidator.hasValue(policyId) || result.containsKey(policyId)) {
                    continue;
                }

                result.put(
                        policyId,
                        new GetOrderedProductResult(
                                item.id(),
                                item.name(),
                                item.brandName()
                        )
                );
            }

            return result;
        } catch (Exception e) {
            log.warn("Failed to fetch product info from product-service: {}", e.getMessage(), e);
            return Map.of();
        }
    }
}
