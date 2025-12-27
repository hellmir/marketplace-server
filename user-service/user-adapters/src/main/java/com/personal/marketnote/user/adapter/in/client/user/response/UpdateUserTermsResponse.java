package com.personal.marketnote.user.adapter.in.client.user.response;

import com.personal.marketnote.user.port.in.result.UpdateUserTermsResult;

import java.util.List;
import java.util.stream.Collectors;

public record UpdateUserTermsResponse(
        List<UpdateUserTermResponse> userTerms
) {
    public static UpdateUserTermsResponse from(UpdateUserTermsResult updateUserTermsResult) {
        return new UpdateUserTermsResponse(updateUserTermsResult.userTerms().stream()
                .map(UpdateUserTermResponse::from)
                .collect(Collectors.toList()));
    }
}
