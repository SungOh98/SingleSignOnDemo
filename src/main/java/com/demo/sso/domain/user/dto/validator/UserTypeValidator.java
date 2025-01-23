package com.demo.sso.domain.user.dto.validator;

import com.demo.sso.domain.user.domain.UserType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserTypeValidator implements ConstraintValidator<ValidUserType, UserType> {

    @Override
    public boolean isValid(UserType userType, ConstraintValidatorContext context) {
        // admin이면 Validation 실패
        return userType != UserType.admin;
    }
}
