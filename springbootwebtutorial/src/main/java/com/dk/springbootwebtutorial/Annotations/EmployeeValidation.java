package com.dk.springbootwebtutorial.Annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EmployeeRoleValidator.class})
public @interface EmployeeValidation {

    String message() default "Role of an employee can be ADMIN OR USER";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
