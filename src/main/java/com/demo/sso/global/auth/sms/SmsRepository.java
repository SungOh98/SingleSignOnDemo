package com.demo.sso.global.auth.sms;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 사용제한
 * 1. 같은 핸드폰으로 여러번 요청하지 못하게 -> 내가 처리
 * 2. 같은 기기에서 번호를 바꿔가며 여러번 요청하지 못하게 -> Client에서 처리
 *
 */
@Repository
public interface SmsRepository extends CrudRepository<Verification, String> {

}
