package org.fenixedu.bennu.social.domain.user;

import java.util.UUID;

import org.fenixedu.bennu.core.domain.User;
import org.fenixedu.bennu.social.domain.api.GoogleAPI;

public class GoogleUser extends GoogleUser_Base {

    public GoogleUser(User user) {
        super();
        setState(UUID.randomUUID().toString());
        setUser(user);
        setGoogleAPI(GoogleAPI.getInstance());
    }

    @Override
    public void delete() {
        /*
         * Warning: if an application saves the oauth token somewhere else,
         * then this method does not guarantees this application won't continue to operate.
         * To revoke access securely please use Google interface
         */
        setUser(null);
        setGoogleAPI(null);
        deleteDomainObject();
    }

}
