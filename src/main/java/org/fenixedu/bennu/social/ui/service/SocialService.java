package org.fenixedu.bennu.social.ui.service;

import org.fenixedu.bennu.core.domain.User;
import org.fenixedu.bennu.social.domain.api.SocialAPI;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pt.ist.fenixframework.Atomic;

@Service
public class SocialService {

    private static final int EXPIRATION_TIME_CONVERSION_MARGIN = 2;
    protected static final Logger LOGGER = LoggerFactory.getLogger(SocialService.class);

    @Atomic(mode = Atomic.TxMode.WRITE)
    public void activateAPI(SocialAPI api, boolean active) {
        api.setActive(active);
    }

    @Atomic(mode = Atomic.TxMode.WRITE)
    public void setAPICredentials(SocialAPI api, String clientID, String clientSecret) {

        if ((api.getClientId() != null && !api.getClientId().equals(clientID))
                || (api.getClientSecret() != null && !api.getClientSecret().equals(clientSecret))) {
            api.revokeAllAccesses();
        }

        api.setClientId(clientID);
        api.setClientSecret(clientSecret);
    }

    @Atomic(mode = Atomic.TxMode.WRITE)
    public void revokeAccounts(SocialAPI api) {
        api.revokeAllAccesses();
    }

    @Atomic(mode = Atomic.TxMode.WRITE)
    public void revokePermission(User user, SocialAPI api) {
        api.revokePermission(user);
    }

    public static DateTime getExpirationDate(int expiresIn) { //static because SocialService cannot have subclasses
        return DateTime.now().plusSeconds(expiresIn - EXPIRATION_TIME_CONVERSION_MARGIN);
    }

}
