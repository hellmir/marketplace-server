package com.personal.marketnote.user.port.out.user;

import com.personal.marketnote.user.domain.user.User;
import com.personal.marketnote.user.exception.UserNotFoundException;

public interface UpdateUserPort {
    void update(User user) throws UserNotFoundException;
}
