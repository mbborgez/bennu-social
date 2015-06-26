package org.fenixedu.bennu.social.domain.user;

import org.fenixedu.bennu.core.domain.User;
import org.fenixedu.bennu.social.domain.api.TwitterAPI;

public class TwitterUser extends TwitterUser_Base {

    public TwitterUser(User user) {
        super();
        setUser(user);
        setTwitterAPI(TwitterAPI.getInstance());
    }

    @Override
    public void delete() {
        /*
         * Warning: if an application saves the oauth token somewhere else,
         * then this method does not guarantees this application won't continue to operate.
         * To revoke access securely please use Twitter interface
         */
        setUser(null);
        setTwitterAPI(null);
        deleteDomainObject();
    }

}
