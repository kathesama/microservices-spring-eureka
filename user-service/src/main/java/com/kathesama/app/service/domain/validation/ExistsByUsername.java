package com.kathesama.app.service.domain.validation;

import com.kathesama.app.service.domain.validation.validatorComponent.ExistsByUsernameComponent;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ExistsByUsernameComponent.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistsByUsername {
    String message() default "Username exists, choose another one";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
