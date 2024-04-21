package com.kathesama.app.service.domain.validation.validatorComponent;

import com.kathesama.app.service.application.service.UserService;
import com.kathesama.app.service.domain.validation.ExistsByUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExistsByUsernameComponent implements ConstraintValidator<ExistsByUsername, String> {
    private final UserService userService;
    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return !userService.existsByUsername(username);
    }
}
