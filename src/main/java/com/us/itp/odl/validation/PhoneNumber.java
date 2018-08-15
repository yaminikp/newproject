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
 * Validation constraint for telephone numbers.
 *
 * <p>This validation is very basic.  For our purposes, a phone number consists of, in order:
 * <ol>
 *   <li>An optional country code, consisting of {@code +} followed by 1-3 digits and a space</li>
 *   <li>A national phone number, consisting of between 2 and 14 digits, optionally broken into any
 *     number of groups by spaces or hyphens.</li>
 * </ol>
 *
 * <p>This should accept most numbers valid according to the
 *   <a href="https://en.wikipedia.org/wiki/E.123">E.123</a> and
 *   <a href="https://en.wikipedia.org/wiki/E.164">E.164</a> standards,
 * but notably does not allow parentheses.
 */
@SuppressWarnings("unused")

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {})

@Pattern(regexp = "([+]\\d{1,3} )?\\d(\\d[- ]?){0,12}\\d")
public @interface PhoneNumber {
    String message() default "{com.us.itp.odl.validation.PhoneNumber.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
