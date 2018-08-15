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
 * Validation constraint forbidding characters not used in typical text.
 *
 * <p>Examples of forbidden characters include non-printable characters, dingbats, and whitespace
 * other than spaces, tabs, and newlines.
 */
@SuppressWarnings("unused")

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {})

@Pattern(regexp = "[ \t\n[^\\p{S}\\p{C}\\p{Z}]]*")
public @interface Text {
    String message() default "{com.us.itp.odl.validation.Text.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
