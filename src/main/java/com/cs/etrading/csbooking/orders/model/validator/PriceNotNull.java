package com.cs.etrading.csbooking.orders.model.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = PriceNotNullValidator.class)
@Documented
public @interface PriceNotNull {
    String message() default "Price is null.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
