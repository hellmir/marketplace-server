package com.personal.shopreward.common.util.http.client.resttemplate;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class RestTemplateErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError();
    }

    @Override
    @SuppressWarnings("removal")
    public void handleError(ClientHttpResponse response) throws IOException {
    }
}
