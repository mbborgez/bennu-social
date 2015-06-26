package org.fenixedu.bennu.social.domain.user;

import java.util.UUID;

import org.fenixedu.bennu.core.domain.User;
import org.fenixedu.bennu.social.domain.api.DropboxAPI;

public class DropboxUser extends DropboxUser_Base {

    public DropboxUser(User user) {
        super();
        setState(UUID.randomUUID().toString());
        setUser(user);
        setDropboxAPI(DropboxAPI.getInstance());
    }

    @Override
    public void delete() {
        /*
         * Warning: if an application saves the oauth token somewhere else,
         * then this method does not guarantees this application won't continue to operate.
         * To revoke access securely please use Dropbox interface
         */
        setUser(null);
        setDropboxAPI(null);
        deleteDomainObject();
    }

}
