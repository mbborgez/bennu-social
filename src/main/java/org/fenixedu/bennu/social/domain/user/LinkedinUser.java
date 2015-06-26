package org.fenixedu.bennu.social.domain.user;

import java.util.UUID;

import org.fenixedu.bennu.core.domain.User;
import org.fenixedu.bennu.social.domain.api.LinkedinAPI;

public class LinkedinUser extends LinkedinUser_Base {

    public LinkedinUser(User user) {
        super();
        setUser(user);
        setLinkedinAPI(LinkedinAPI.getInstance());
        setState(UUID.randomUUID().toString());
        setAuthorizedScopes(LinkedinAPI.getInstance().getBindedScopes());
    }

    @Override
    public void delete() {
        /*
         * Warning: if an application saves the oauth token somewhere else,
         * then this method does not guarantees this application won't continue to operate.
         * To revoke access securely please use Linkedin interface
         */
        setUser(null);
        setLinkedinAPI(null);
        deleteDomainObject();
    }

}
