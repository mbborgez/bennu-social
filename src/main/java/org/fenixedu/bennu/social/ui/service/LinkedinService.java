package org.fenixedu.bennu.social.ui.service;

import java.util.Optional;

import org.fenixedu.bennu.core.domain.User;
import org.fenixedu.bennu.social.domain.api.LinkedinAPI;
import org.fenixedu.bennu.social.domain.user.LinkedinUser;
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
public class LinkedinService {

    protected static final Logger LOGGER = LoggerFactory.getLogger(LinkedinService.class);

    @Atomic(mode = Atomic.TxMode.WRITE)
    public LinkedinAPI getInstance() {
        return LinkedinAPI.getInstance();
    }

    @Atomic(mode = Atomic.TxMode.WRITE)
    public String getAuthenticationUrlForUser(User user) {
        return getInstance().getAuthenticationUrlForUser(user);
    }

    public ResponseEntity<String> makeAccessTokenRequest(String code) {
        LinkedinAPI linkedinAPI = getInstance();
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> request = linkedinAPI.getAccessTokenRequest(code);
        return restTemplate.postForEntity(linkedinAPI.getAccessTokenUrl(), request, String.class);
    }

    @Atomic(mode = Atomic.TxMode.WRITE)
    public void parseResponse(LinkedinUser user, ResponseEntity<String> rsp) throws AccessTokenNotProvidedException {
        String body = rsp.getBody();

        LOGGER.info("Access token request answer from Linkedin: " + body);

        JsonObject json = new JsonParser().parse(body).getAsJsonObject();

        if (!json.has("access_token")) {
            LOGGER.error("Access token (access_token) has not been returned by Linkedin API. Instead: " + body);
            throw new AccessTokenNotProvidedException(body);
        }

        String accessToken = json.get("access_token").getAsString();
        int expiresIn = json.get("expires_in").getAsInt();

        user.setAccessToken(accessToken);

        DateTime expirationDate = SocialService.getExpirationDate(expiresIn);
        user.setExpirationDate(expirationDate);
    }

    @Atomic(mode = Atomic.TxMode.WRITE)
    public LinkedinUser getAuthenticatedUser(User user) {
        Optional<LinkedinUser> linkedinUser = getInstance().getAuthenticatedUser(user);

        if (!linkedinUser.isPresent()) {
            return new LinkedinUser(user);
        }
        return linkedinUser.get();
    }
}
