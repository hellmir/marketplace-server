package com.personal.marketnote.user.port.in.result;

import com.personal.marketnote.user.domain.user.UserTerms;

import java.util.List;
import java.util.stream.Collectors;

public record UpdateUserTermsResult(
        List<UpdateUserTermResult> userTerms
) {
    public static UpdateUserTermsResult from(List<UserTerms> userTerms) {
        return new UpdateUserTermsResult(
                userTerms.stream().map(UpdateUserTermResult::from)
                        .collect(Collectors.toList())
        );
    }
}
