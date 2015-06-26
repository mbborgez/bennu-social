package org.fenixedu.bennu.social.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresGithub {

    /*
     * Scopes should be used according Github documentation since it will be used in requests.
     * Misuse may lead to unexpected errors
     * See https://developer.github.com/v3/oauth/#scopes (link may be outdated)
     */
    String[] scopes() default {};
}
