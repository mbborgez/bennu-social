package org.fenixedu.bennu.social.ui.service;

import java.util.Optional;

import org.fenixedu.bennu.core.domain.User;
import org.fenixedu.bennu.social.domain.api.BitbucketAPI;
import org.fenixedu.bennu.social.domain.user.BitbucketUser;
import org.fenixedu.bennu.social.exception.AccessTokenNotProvidedException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import pt.ist.fenixframework.Atomic;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class BitbucketService {

    protected static final Logger LOGGER = LoggerFactory.getLogger(BitbucketService.class);

    @Atomic(mode = Atomic.TxMode.WRITE)
    public BitbucketAPI getInstance() {
        return BitbucketAPI.getInstance();
    }

    @Atomic(mode = Atomic.TxMode.WRITE)
    public String getAuthenticationUrlForUser(User user) {
        return getInstance().getAuthenticationUrlForUser(user);
    }

    @Atomic(mode = Atomic.TxMode.WRITE)
    public void parseResponse(BitbucketUser user, ResponseEntity<String> rsp) throws AccessTokenNotProvidedException {
        String body = rsp.getBody();

        LOGGER.info("Access token request answer from Bitbucket: " + body);

        JsonObject json = new JsonParser().parse(body).getAsJsonObject();

        if (!json.has("access_token")) {
            LOGGER.error("Access token (access_token) has not been returned by Bitbucket API. Instead: " + body);
            throw new AccessTokenNotProvidedException(body);
        }

        String accessToken = json.get("access_token").getAsString();
        String tokenType = json.get("token_type").getAsString();
        String scopes = json.get("scopes").getAsString();
        String refreshToken = json.get("refresh_token").getAsString();
        int expiresIn = json.get("expires_in").getAsInt();

        DateTime expirationDate = SocialService.getExpirationDate(expiresIn);

        user.setExpirationDate(expirationDate);
        user.setAccessToken(accessToken);
        user.setTokenType(tokenType);
        user.setRefreshToken(refreshToken);

        user.setAuthorizedScopes(scopes);
    }

    @Atomic(mode = Atomic.TxMode.WRITE)
    public BitbucketUser getAuthenticatedUser(User user) {
        Optional<BitbucketUser> bitbucketUser = getInstance().getAuthenticatedUser(user);

        if (!bitbucketUser.isPresent()) {
            return new BitbucketUser(user);
        }
        return bitbucketUser.get();
    }

    public ResponseEntity<String> makeAccessTokenRequest(String code) {
        BitbucketAPI bitbucketAPI = getInstance();
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> request = bitbucketAPI.getAccessTokenRequest(code);
        return restTemplate.postForEntity(bitbucketAPI.getAccessTokenUrl(), request, String.class);
    }
}
