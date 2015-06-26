package org.fenixedu.bennu.social.domain.user;

import java.util.Arrays;
import java.util.UUID;

import org.fenixedu.bennu.core.domain.User;
import org.fenixedu.bennu.social.domain.api.GithubAPI;

public class GithubUser extends GithubUser_Base {

    public GithubUser(User user) {
        super();
        setState(UUID.randomUUID().toString());
        setUser(user);
        setGithubAPI(GithubAPI.getInstance());
        setAskedScopes(GithubAPI.getInstance().getBindedScopes());
    }

    public boolean wasAskedScope(String scope) {
        return Arrays.asList(getAskedScopes().split(",")).contains(scope);
    }

    public boolean hasAcceptedScope(String scope) {
        return Arrays.asList(getAuthorizedScopes().split(",")).contains(scope);
    }

    public boolean hasRejecteddScope(String scope) {
        return wasAskedScope(scope) && !hasAcceptedScope(scope);
    }

    @Override
    public void delete() {
        /*
         * Warning: if an application saves the oauth token somewhere else,
         * then this method does not guarantees this application won't continue to operate.
         * To revoke access securely please use Github interface
         */
        setUser(null);
        setGithubAPI(null);
        deleteDomainObject();
    }
}
