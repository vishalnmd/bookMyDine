package com.userservice.user.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.*;

/**
 * This is password validator
 */
@Documented
//@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Pattern(
    regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,}$",
    message = "Password must contain uppercase, lowercase, number and special character"
)
public @interface Password {

    String message() default
        "Password must contain uppercase, lowercase, number and special character";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
