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
 * Validation constraint for passwords.
 *
 * <p>Passwords are required to satisfy these requirements:
 * <ol>
 *   <li>Minimum 8 characters in length.</li>
 *   <li>Contains at least one lower-case letter</li>
 *   <li>Contains at least one upper-case letter</li>
 *   <li>Contains at least one numeral</li>
 *   <li>Contains at least one punctuation character or symbol</li>
 *   <li>Contains no whitespace or non-printable characters</li>
 * </ol>
 *
 * <p>All of these requirements are interpreted as widely as possible.  In particular, non-Latin
 * scripts are allowed (although scripts without casing do not participate in either the lower-case
 * or upper-case letters and must therefore be combined with other scripts).
 */
@SuppressWarnings("unused")

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {})

@Size(min = 8)
@Pattern(regexp = ".*\\p{Ll}.*") // Require a lowercase letter
@Pattern(regexp = ".*[\\p{Lu}\\p{Lt}].*") // Require an upper- or title-cased letter
@Pattern(regexp = ".*\\p{Nd}.*") // Require a numeral
@Pattern(regexp = ".*[\\p{P}\\p{S}].*") // Require a punctuation or symbol character
@Pattern(regexp = "[^\\p{Z}\\p{C}]+") // Disallow whitespace and control characters
public @interface Password {
    String message() default "{com.us.itp.odl.validation.Password.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
