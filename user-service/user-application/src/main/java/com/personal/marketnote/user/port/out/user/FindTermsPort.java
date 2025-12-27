package com.personal.marketnote.user.port.out.user;

import com.personal.marketnote.user.domain.user.Terms;

import java.util.List;

public interface FindTermsPort {
    List<Terms> findAll();
}
