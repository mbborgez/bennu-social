package org.fenixedu.bennu.social.ui.service;

import java.util.Optional;

import org.fenixedu.bennu.core.domain.User;
import org.fenixedu.bennu.social.domain.api.TwitterAPI;
import org.fenixedu.bennu.social.domain.user.TwitterUser;
import org.fenixedu.bennu.social.exception.AccessTokenNotProvidedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import pt.ist.fenixframework.Atomic;
import twitter4j.auth.AccessToken;

@Service
public class TwitterService {

    protected static final Logger LOGGER = LoggerFactory.getLogger(TwitterService.class);

    @Atomic(mode = Atomic.TxMode.WRITE)
    public TwitterAPI getInstance() {
        return TwitterAPI.getInstance();
    }

    @Atomic(mode = Atomic.TxMode.WRITE)
    public String getAuthenticationUrlForUser(User user) {
        return getInstance().getAuthenticationUrlForUser(user);
    }

    @Atomic(mode = Atomic.TxMode.WRITE)
    public void parseResponse(TwitterUser twitterUser, AccessToken oAuthAccessToken) throws AccessTokenNotProvidedException {

        String token = oAuthAccessToken.getToken();
        String tokenSecret = oAuthAccessToken.getTokenSecret();
        String screenName = oAuthAccessToken.getScreenName();

        LOGGER.info("Access token request answer from Twitter: Token=" + token + " and TokenSecret=" + tokenSecret
                + " and ScreenName=" + screenName);

        if (token == null) {
            LOGGER.error("Access token has not been returned by Twitter API");
            throw new AccessTokenNotProvidedException(null);
        }

        twitterUser.setAccessToken(token);
        twitterUser.setTokenSecret(tokenSecret);
        twitterUser.setScreenName(screenName);
    }

    @Atomic(mode = Atomic.TxMode.WRITE)
    public TwitterUser getAuthenticatedUser(User user) {
        Optional<TwitterUser> twitterUser = getInstance().getAuthenticatedUser(user);

        if (!twitterUser.isPresent()) {
            return new TwitterUser(user);
        }

        return twitterUser.get();
    }

}
