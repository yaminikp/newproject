package com.us.itp.odl.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Validation constraint for postal codes.
 *
 * <p>Note that international zip codes are not currently supported; in particular, letters are
 * currently disallowed.  Indian PIN codes and US ZIP codes are both fully supported, however.
 */
@SuppressWarnings("unused")

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {})

@Pattern(regexp = "[0-9]+(-[0-9]+)?")
@Size(min = 4, max = 10)
public @interface PostalCode {
    String message() default "{com.us.itp.odl.validation.PostalCode.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
