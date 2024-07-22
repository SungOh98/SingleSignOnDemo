package com.demo.sso.domain.user.dao;

import com.demo.sso.global.auth.jwt.UserToken;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserTokenMemoryRepository implements UserTokenRepository{
    private static final Map<Long, UserToken> store = new HashMap<>();

    @Override
    public UserToken save(UserToken userToken) {
        return store.put(userToken.getUserId(), userToken);
    }

    @Override
    public Optional<UserToken> findById(Long userId) {
//        UserToken findToken = store.get(userId);
        return Optional.ofNullable(store.get(userId));
    }

    @Override
    public void deleteById(Long userId) {
        store.remove(userId);
    }

    @Override
    public void addTokenToBlackList(Long userId, String token) {

    }
}
