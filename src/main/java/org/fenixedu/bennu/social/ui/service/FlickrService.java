package org.fenixedu.bennu.social.ui.service;

import java.util.Optional;

import org.fenixedu.bennu.core.domain.User;
import org.fenixedu.bennu.social.domain.api.FlickrAPI;
import org.fenixedu.bennu.social.domain.user.FlickrUser;
import org.fenixedu.bennu.social.exception.AccessTokenNotProvidedException;
import org.scribe.model.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pt.ist.fenixframework.Atomic;

@Service
public class FlickrService {

    protected static final Logger LOGGER = LoggerFactory.getLogger(FlickrService.class);

    @Atomic(mode = Atomic.TxMode.WRITE)
    public FlickrAPI getInstance() {
        return FlickrAPI.getInstance();
    }

    @Atomic(mode = Atomic.TxMode.WRITE)
    public String getAuthenticationUrlForUser(User user) {
        return getInstance().getAuthenticationUrlForUser(user);
    }

    @Atomic(mode = Atomic.TxMode.WRITE)
    public void parseResponse(FlickrUser flickrUser, Token accessToken) throws AccessTokenNotProvidedException {

        String token = accessToken.getToken();
        String tokenSecret = accessToken.getSecret();

        LOGGER.info("Access token request answer from Flickr: Token=" + token + " and TokenSecret=" + tokenSecret);

        if (token == null) {
            LOGGER.error("Access token has not been returned by Flickr API");
            throw new AccessTokenNotProvidedException(null);
        }

        flickrUser.setAccessToken(token);
        flickrUser.setAccessTokenSecret(tokenSecret);
    }

    @Atomic(mode = Atomic.TxMode.WRITE)
    public FlickrUser getAuthenticatedUser(User user) {
        Optional<FlickrUser> flickrUser = getInstance().getAuthenticatedUser(user);

        if (!flickrUser.isPresent()) {
            return new FlickrUser(user);
        }

        return flickrUser.get();
    }

}
