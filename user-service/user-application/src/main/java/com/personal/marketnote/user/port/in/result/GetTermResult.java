package com.personal.marketnote.user.port.in.result;

import com.personal.marketnote.user.domain.user.Terms;

public record GetTermResult(
        Long id,
        String content
) {
    public static GetTermResult from(Terms terms) {
        return new GetTermResult(terms.getId(), terms.getContent());
    }
}
