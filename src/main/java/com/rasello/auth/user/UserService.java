package com.rasello.auth.user;

import com.rasello.auth.exception.ExistingRecordException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface UserService extends UserDetailsService {
    User getUser(UUID id);

    User getByEmail(String email);

    List<User> getAll();

    Page<User> getAll(Pageable pageRequest);

    User create(User user) throws ExistingRecordException;
}
