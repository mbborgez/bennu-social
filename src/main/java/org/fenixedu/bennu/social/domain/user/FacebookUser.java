package org.fenixedu.bennu.social.domain.user;

import java.util.UUID;

import org.fenixedu.bennu.core.domain.User;
import org.fenixedu.bennu.social.domain.api.FacebookAPI;

public class FacebookUser extends FacebookUser_Base {

    public FacebookUser(User user) {
        super();
        setUser(user);
        setFacebookAPI(FacebookAPI.getInstance());
        setState(UUID.randomUUID().toString());
    }

    @Override
    public void delete() {
        /*
         * Warning: if an application saves the oauth token somewhere else,
         * then this method does not guarantees this application won't continue to operate.
         * To revoke access securely please use Facebook interface
         */
        setUser(null);
        setFacebookAPI(null);
        deleteDomainObject();
    }

}
