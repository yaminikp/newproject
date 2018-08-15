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

/**
 * Validation constraint allowing only alphabetic characters, spaces, and appropriate punctuation.
 *
 * <p>"Allowed punctuation" consists of periods, apostrophes, hyphens, and forward slashes.
 *
 * <p>Note that the English alphabet is not assumed; alphabetic characters in other scripts are
 * allowed, as are diacritics.
 */
@SuppressWarnings("unused")

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {})

@AlphanumericName
@Pattern(regexp = "\\P{N}*")
public @interface Name {
    String message() default "{com.us.itp.odl.validation.Name.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
