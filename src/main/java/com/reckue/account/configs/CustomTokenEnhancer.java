package com.reckue.account.configs;

import com.reckue.account.models.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class CustomTokenEnhancer represents a custom access token converter.
 *
 * @author Kamila Meshcheryakova
 */
public class CustomTokenEnhancer extends JwtAccessTokenConverter {

    /**
     * This method allows to add additional information to token.
     *
     * @param accessToken JWT
     * @param authentication OAuth2Authentication
     * @return the object of OAuth2AccessToken class with changes
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        Map<String, Object> info = new LinkedHashMap<>(accessToken.getAdditionalInformation());
        info.put("userId", user.getId());
        info.put("email", user.getEmail());
        info.put("username", user.getUsername());

        DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
        customAccessToken.setAdditionalInformation(info);

        return super.enhance(customAccessToken, authentication);
    }
}
