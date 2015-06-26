package org.fenixedu.bennu.social.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresFacebook {

    /*
     * Scopes should be used according Facebook documentation since it will be used in requests.
     * Misuse may lead to unexpected errors
     * See https://developers.facebook.com/docs/facebook-login/permissions/v2.4 (link may be outdated)
     */
    String[] scopes() default {};
}
