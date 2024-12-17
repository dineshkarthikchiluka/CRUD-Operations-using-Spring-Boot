package com.dk.springbootwebtutorial.Annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class EmployeeRoleValidator implements ConstraintValidator<EmployeeValidation,String> {

    @Override
    public boolean isValid(String inputRole, ConstraintValidatorContext constraintValidatorContext) {
        if(inputRole == null){
            return false;
        }
        List<String> roles = List.of("ADMIN","USER");
        return roles.contains(inputRole);
    }
}
