package com.personal.marketnote.user.port.out.terms;

import com.personal.marketnote.user.domain.user.Terms;

import java.util.List;

public interface FindUserTermsPort {
    List<Terms> findAll();
}
