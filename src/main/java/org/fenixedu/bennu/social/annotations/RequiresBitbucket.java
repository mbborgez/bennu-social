package org.fenixedu.bennu.social.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresBitbucket {

    /*
     * Scopes should be used in a way the user can configure correctly the application on Bitbucket interface.
     * Bad configuration may lead to unexpected errors
     * See https://confluence.atlassian.com/display/BITBUCKET/Integrate+another+application+through+OAuth (link may be outdated)
     */
    BitbucketScopes[] scopes() default {};

    public enum BitbucketScopes {

        ACCOUNT_EMAIL,

        ACCOUNT_R,

        ACCOUNT_W,

        TEAM_R,

        TEAM_W,

        REPO_R,

        REPO_W,

        REPO_ADMIN,

        PULL_R,

        PULL_W,

        ISSUES_R,

        ISSUES_W,

        WIKI,

        SNIPPETS_R,

        SNIPPETS_W,

        WEBHOOKS;

        public String getQualifiedName() {
            return BitbucketScopes.class.getSimpleName() + "." + name();
        }
    }
}
