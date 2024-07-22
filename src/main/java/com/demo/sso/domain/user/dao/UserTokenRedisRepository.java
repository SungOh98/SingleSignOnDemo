package com.demo.sso.domain.user.dao;

import com.demo.sso.global.auth.jwt.UserToken;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Primary
@Repository
public interface UserTokenRedisRepository extends CrudRepository<UserToken, Long>, UserTokenRepository {
    @Override
    UserToken save(UserToken userToken);

    @Override
    Optional<UserToken> findById(Long userId);

    @Override
    void deleteById(Long userId);

    @Override
    void addTokenToBlackList(Long userId, String token);
}
