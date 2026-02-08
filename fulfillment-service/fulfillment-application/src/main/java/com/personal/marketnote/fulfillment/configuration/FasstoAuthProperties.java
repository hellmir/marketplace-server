package com.personal.marketnote.fulfillment.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "vendor.fassto.auth")
public class FasstoAuthProperties {
    private String baseUrl;
    private String customerCode;
    private String apiCd;
    private String apiKey;
    private String connectPath = "/api/v1/auth/connect";
    private String disconnectPath = "/api/v1/auth/disconnect";
    private String shopPath = "/api/v1/marketnote/{customerCode}";
    private String supplierPath = "/api/v1/supplier/{customerCode}";
    private String goodsPath = "/api/v1/goods/{customerCode}";
    private String goodsElementPath = "/api/v1/goods/element/{customerCode}";
    private String warehousingPath = "/api/v1/warehousing/{customerCode}";
    private String warehousingListPath = "/api/v1/warehousing/{customerCode}/{startDate}/{endDate}";
    private String stockListPath = "/api/v1/stock/list/{customerCode}";
    private String settlementDailyCostPath = "/api/v1/settlement/{yearMonth}/{whCd}/{customerCode}";
}
