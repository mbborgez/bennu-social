package org.fenixedu.bennu.social.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresGoogle {

    /*
     * Scopes should be used according Google documentation since it will be used in requests.
     * Misuse may lead to unexpected errors
     * See https://developers.google.com/oauthplayground for more information (link may be outdated)
     */
    String[] scopes() default {};
}
