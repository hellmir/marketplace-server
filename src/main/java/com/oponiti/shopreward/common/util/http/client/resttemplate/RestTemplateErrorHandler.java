package com.oponiti.shopreward.common.util.http.client.resttemplate;

import java.io.IOException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

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
