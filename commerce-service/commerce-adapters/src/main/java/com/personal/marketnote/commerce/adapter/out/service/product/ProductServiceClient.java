package com.personal.marketnote.commerce.adapter.out.service.product;

import com.personal.marketnote.commerce.adapter.out.service.product.response.OrderedProductsResponse;
import com.personal.marketnote.commerce.adapter.out.service.product.response.ProductsInfoResponse;
import com.personal.marketnote.commerce.port.out.product.FindProductByPricePolicyPort;
import com.personal.marketnote.commerce.port.out.result.product.ProductInfoResult;
import com.personal.marketnote.common.adapter.in.api.format.BaseResponse;
import com.personal.marketnote.common.adapter.in.response.CursorResponse;
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
    public Map<Long, ProductInfoResult> findByPricePolicyIds(List<Long> pricePolicyIds) {
        if (!FormatValidator.hasValue(pricePolicyIds)) {
            return Map.of();
        }

        URI uri = UriComponentsBuilder.fromUriString(productServiceBaseUrl)
                .path("/api/v1/products")
                .queryParam("pricePolicyIds", pricePolicyIds.toArray())
                .queryParam("pageSize", pricePolicyIds.size())
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(adminAccessToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        return sendRequest(uri, request);
    }

    private Map<Long, ProductInfoResult> sendRequest(URI uri, HttpEntity<Void> request) {
        try {
            ResponseEntity<BaseResponse<OrderedProductsResponse>> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    request,
                    new ParameterizedTypeReference<>() {
                    }
            );

            List<ProductsInfoResponse> productsInfo = unboxResponse(response);

            Map<Long, ProductInfoResult> productInfoResult = new HashMap<>();
            generateResult(productInfoResult, productsInfo);

            return productInfoResult;
        } catch (Exception e) {
            log.warn("Failed to fetch product info from product-service: {}", e.getMessage(), e);
            return Map.of();
        }
    }

    private List<ProductsInfoResponse> unboxResponse(ResponseEntity<BaseResponse<OrderedProductsResponse>> response) {
        BaseResponse<OrderedProductsResponse> body = response.getBody();
        if (!FormatValidator.hasValue(body)) {
            return List.of();
        }

        OrderedProductsResponse content = body.getContent();
        if (!FormatValidator.hasValue(content)) {
            return List.of();
        }

        CursorResponse<ProductsInfoResponse> products = content.products();
        if (!FormatValidator.hasValue(products)) {
            return List.of();
        }

        return products.items();
    }

    private void generateResult(Map<Long, ProductInfoResult> productInfoResult, List<ProductsInfoResponse> productsInfo) {
        for (ProductsInfoResponse productInfo : productsInfo) {
            if (!FormatValidator.hasValue(productInfo) || !FormatValidator.hasValue(productInfo.pricePolicy())) {
                continue;
            }

            Long policyId = productInfo.getPricePolicyId();
            if (!FormatValidator.hasValue(policyId) || productInfoResult.containsKey(policyId)) {
                continue;
            }

            productInfoResult.put(
                    policyId,
                    new ProductInfoResult(
                            productInfo.name(),
                            productInfo.brandName(),
                            productInfo.selectedOptions()
                    )
            );
        }
    }
}
