package org.fenixedu.bennu.social.domain.user;

import org.fenixedu.bennu.core.domain.User;
import org.fenixedu.bennu.social.domain.api.FlickrAPI;

public class FlickrUser extends FlickrUser_Base {

    public FlickrUser(User user) {
        super();
        setUser(user);
        setFlickrAPI(FlickrAPI.getInstance());
    }

    @Override
    public void delete() {
        /*
         * Warning: if an application saves the oauth token somewhere else,
         * then this method does not guarantees this application won't continue to operate.
         * To revoke access securely please use Flickr 'Authorized applications' interface
         */
        setUser(null);
        setFlickrAPI(null);
        deleteDomainObject();
    }

}
