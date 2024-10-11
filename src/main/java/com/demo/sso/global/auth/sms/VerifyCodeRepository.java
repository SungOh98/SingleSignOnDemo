package com.demo.sso.global.auth.sms;

import com.demo.sso.global.auth.exception.ExpiredCodeException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface VerifyCodeRepository extends CrudRepository<VerifyCode, String> {
    /* 기본 제공 메소드들 */
    @Override
    VerifyCode save(VerifyCode verifyCode);

    @Override
    Optional<VerifyCode> findById(String id);

    @Override
    void deleteById(String id);

    @Override
    boolean existsById(String phone);

    /* 새롭게 정의한 메소드들 */
    default VerifyCode findByPhone(String phone) {
        return findById(phone)
                .orElseThrow(() -> ExpiredCodeException.withDetail(phone));
    }

    default void deleteByPhone(String phone) {
        deleteById(phone);
    }

    default boolean isExist(String phone) {
        return existsById(phone);
    }



}
