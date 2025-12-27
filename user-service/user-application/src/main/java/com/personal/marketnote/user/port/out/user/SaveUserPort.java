package com.personal.marketnote.user.port.out.user;

import com.personal.marketnote.user.domain.user.User;

public interface SaveUserPort {
    User save(User user);
}
