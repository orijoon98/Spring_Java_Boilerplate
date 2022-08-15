package com.boilerplate.domain.user.repository;

import com.boilerplate.domain.user.User;
import com.boilerplate.domain.user.UserSocialType;

public interface UserRepositoryCustom {

    boolean existsBySocialIdAndSocialType(String socialId, UserSocialType socialType);

    User findUserById(Long id);

    User findUserBySocialIdAndSocialType(String socialId, UserSocialType socialType);
}
