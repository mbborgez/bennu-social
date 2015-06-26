package org.fenixedu.bennu.social.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresTwitter {

    /*
     * Scopes should be used in a way the user can configure correctly the application on Twitter interface.
     * Bad configuration may lead to unexpected errors
     * See https://dev.twitter.com/oauth/overview/application-permission-model (link may be outdated)
     */
    TwitterScopes scope() default TwitterScopes.READ;

    public enum TwitterScopes {

        READ,

        WRITE,

        MESSAGES;

        public String getQualifiedName() {
            return TwitterScopes.class.getSimpleName() + "." + name();
        }
    }
}
