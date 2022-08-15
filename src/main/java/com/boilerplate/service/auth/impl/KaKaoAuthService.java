package com.boilerplate.service.auth.impl;

import com.boilerplate.common.util.HttpHeaderUtils;
import com.boilerplate.domain.user.User;
import com.boilerplate.domain.user.UserSocialType;
import com.boilerplate.domain.user.repository.UserRepository;
import com.boilerplate.external.client.kakao.KakaoApiClient;
import com.boilerplate.external.client.kakao.dto.response.KakaoProfileResponse;
import com.boilerplate.service.auth.AuthService;
import com.boilerplate.service.auth.dto.request.LoginDto;
import com.boilerplate.service.user.UserService;
import com.boilerplate.service.user.UserServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KaKaoAuthService implements AuthService {

    private static final UserSocialType socialType = UserSocialType.KAKAO;

    private final KakaoApiClient kaKaoApiCaller;

    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public Long login(LoginDto request) {
        KakaoProfileResponse response = kaKaoApiCaller.getProfileInfo(HttpHeaderUtils.withBearerToken(request.getToken()));
        User user = UserServiceUtils.findUserBySocialIdAndSocialType(userRepository, response.getId(), socialType);
        if (user == null)
            return userService.registerUser(request.toCreateUserDto(response.getId()));
        return user.getId();
    }
}
