package com.boilerplate.service.auth;

import com.boilerplate.service.auth.dto.request.LoginDto;

public interface AuthService {

    Long login(LoginDto request);
}
