package com.personal.marketnote.user.port.out.user;

import com.personal.marketnote.common.exception.UserNotFoundException;
import com.personal.marketnote.user.domain.user.User;

public interface UpdateUserPort {
    void update(User user) throws UserNotFoundException;
}
