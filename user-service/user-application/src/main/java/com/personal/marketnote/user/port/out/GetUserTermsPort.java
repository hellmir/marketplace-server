package com.personal.marketnote.user.port.out;

import com.personal.marketnote.user.domain.user.Terms;

import java.util.List;

public interface GetUserTermsPort {
    List<Terms> getAllTerms();
}
