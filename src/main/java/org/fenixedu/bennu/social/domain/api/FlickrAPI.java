package org.fenixedu.bennu.social.domain.api;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.fenixedu.bennu.core.domain.Bennu;
import org.fenixedu.bennu.core.domain.User;
import org.fenixedu.bennu.core.util.CoreConfiguration;
import org.fenixedu.bennu.social.domain.user.FlickrUser;
import org.scribe.model.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.Atomic.TxMode;
import pt.ist.fenixframework.FenixFramework;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.REST;

public class FlickrAPI extends FlickrAPI_Base {

    protected static final Logger LOGGER = LoggerFactory.getLogger(FlickrAPI.class);

    private static final String CALLBACK_URL = CoreConfiguration.getConfiguration().applicationUrl()
            + "/bennu-social/flickr/callback";
    private static final String AUTH_URL = "https://www.flickr.com/services/oauth/authorize";

    private static final SortedSet<String> SCOPES = new TreeSet<String>();

    private FlickrAPI() {
        super();
        setBennu(Bennu.getInstance());
    }

    public static FlickrAPI getInstance() {
        if (Bennu.getInstance().getFlickrAPI() == null) {
            return initialize();
        }
        return Bennu.getInstance().getFlickrAPI();
    }

    @Atomic(mode = TxMode.WRITE)
    private static FlickrAPI initialize() {
        if (Bennu.getInstance().getFlickrAPI() == null) {
            return new FlickrAPI();
        }
        return Bennu.getInstance().getFlickrAPI();
    }

    @Override
    public void revokeAllAccesses() {

        Set<FlickrUser> users = getFlickrUserSet();

        LOGGER.info("Revoking accesses for Flickr API: Number of accounts = " + users.size());

        users.stream().forEach(u -> {
            u.delete();
        });
    }

    @Override
    public void revokePermission(User user) {
        Optional<FlickrUser> authenticatedUser = getAuthenticatedUser(user);

        LOGGER.info("Revoking permission for Flickr API by user: " + user.getUsername() + ". Existent permission? "
                + authenticatedUser.isPresent());

        if (authenticatedUser.isPresent()) {
            authenticatedUser.get().delete();
        }
    }

    @Override
    public String getAuthenticationUrlForUser(User user) {

        String apiKey = getClientId();
        String sharedSecret = getClientSecret();
        Flickr f = new Flickr(apiKey, sharedSecret, new REST());

        Token requestToken = f.getAuthInterface().getRequestToken(getCallbackURL());
        String secret = requestToken.getSecret();
        String token = requestToken.getToken();

        Optional<FlickrUser> flickrUser = getAuthenticatedUser(user);

        if (!flickrUser.isPresent()) {
            flickrUser = Optional.of(new FlickrUser(user));
        }

        flickrUser.get().setToken(token);
        flickrUser.get().setSecret(secret);

        String perms = makeScopes(getScopes());

        return AUTH_URL + "?oauth_token=" + token + "&perms=" + perms;
    }

    @Override
    public Optional<FlickrUser> getAuthenticatedUser(User user) {

        if (!getActive()) {
            return Optional.empty();
        }

        return getFlickrUserSet().stream().filter(u -> u.getUser().equals(user)).findFirst();
    }

    @Override
    public String getCallbackURL() {
        return CALLBACK_URL;
    }

    public static void ensureConsistentScope() {
        FenixFramework
                .atomic(() -> {
                    FlickrAPI instance = getInstance();

                    LOGGER.info("Checking old vs new Flickr scope " + instance.getBindedScopes() + " vs "
                            + makeScopes(instance.getScopes()));

                    if (instance.getBindedScopes() != null
                            && !makeScopes(instance.getScopes()).equals(instance.getBindedScopes())) {
                        LOGGER.warn("Flickr API has changed scopes. Endpoint invocations may fail if users do not review their access for the application");
                    }
                    instance.setBindedScopes(makeScopes(instance.getScopes()));
                });
    }

    protected Set<String> getScopes() {
        return Collections.unmodifiableSet(SCOPES);
    }

    public static void appendScopes(String scope) {
        SCOPES.add(scope);
    }

    public static String makeScopes(Collection<String> scopes) {

        if (scopes.contains("delete")) {
            return "delete";
        }

        if (scopes.contains("write")) {
            return "write";
        }

        return "read";
    }

}
