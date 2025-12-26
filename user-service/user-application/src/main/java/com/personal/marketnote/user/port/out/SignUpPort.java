package com.personal.marketnote.user.port.out;

import com.personal.marketnote.user.domain.user.User;

public interface SignUpPort {
    User saveUser(User user);
}
