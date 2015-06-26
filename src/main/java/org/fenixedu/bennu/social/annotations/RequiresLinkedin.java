package org.fenixedu.bennu.social.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresLinkedin {

    /*
     * Scopes should be used according Linkedin documentation since it will be used in requests.
     * Misuse may lead to unexpected errors
     * See https://developer.linkedin.com (link may be outdated)
     */
    String[] scopes() default {};
}
