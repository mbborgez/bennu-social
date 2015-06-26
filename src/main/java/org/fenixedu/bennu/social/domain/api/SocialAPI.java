package org.fenixedu.bennu.social.domain.api;

import java.util.Optional;

import org.fenixedu.bennu.core.domain.User;
import org.fenixedu.bennu.social.domain.user.SocialUser;

public abstract class SocialAPI extends SocialAPI_Base {

    public SocialAPI() {
        super();
    }

    public boolean isConfigured() {
        return getClientId() != null && !getClientId().isEmpty() && getClientSecret() != null && !getClientSecret().isEmpty();
    }

    public abstract void revokeAllAccesses();

    public abstract void revokePermission(User user);

    public abstract Optional<? extends SocialUser> getAuthenticatedUser(User user);

    public boolean isUserAuthenticated(User user) {
        // Warning: may return true if user has revoked authorization manually
        Optional<? extends SocialUser> authenticatedUser = getAuthenticatedUser(user);
        return authenticatedUser.isPresent() && authenticatedUser.get().getAccessToken() != null;
    }

    public abstract String getAuthenticationUrlForUser(User user);

    public abstract String getCallbackURL();

}
