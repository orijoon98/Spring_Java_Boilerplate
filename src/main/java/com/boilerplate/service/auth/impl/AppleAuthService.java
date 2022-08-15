package com.boilerplate.service.auth.impl;

import com.boilerplate.domain.user.User;
import com.boilerplate.domain.user.UserSocialType;
import com.boilerplate.domain.user.repository.UserRepository;
import com.boilerplate.external.client.apple.AppleTokenProvider;
import com.boilerplate.service.auth.AuthService;
import com.boilerplate.service.auth.dto.request.LoginDto;
import com.boilerplate.service.user.UserService;
import com.boilerplate.service.user.UserServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AppleAuthService implements AuthService {

    private static final UserSocialType socialType = UserSocialType.APPLE;

    private final AppleTokenProvider appleTokenDecoder;

    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public Long login(LoginDto request) {
        String socialId = appleTokenDecoder.getSocialIdFromIdToken(request.getToken());
        User user = UserServiceUtils.findUserBySocialIdAndSocialType(userRepository, socialId, socialType);
        if (user == null) return userService.registerUser(request.toCreateUserDto(socialId));
        return user.getId();
    }
}
