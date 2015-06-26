package org.fenixedu.bennu.social.domain.user;

import org.fenixedu.bennu.core.domain.User;
import org.fenixedu.bennu.social.domain.api.BitbucketAPI;

public class BitbucketUser extends BitbucketUser_Base {

    public BitbucketUser(User user) {
        super();
        setUser(user);
        setBitbucketAPI(BitbucketAPI.getInstance());
    }

    @Override
    public void delete() {
        /*
         * Warning: if an application saves the oauth token somewhere else,
         * then this method does not guarantees this application won't continue to operate.
         * To revoke access securely please use Bitbucket interface
         */
        setUser(null);
        setBitbucketAPI(null);
        deleteDomainObject();
    }

}
