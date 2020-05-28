package us.lacchain.crossborder.management.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import us.lacchain.crossborder.management.model.UserAuthenticated;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomTokenEnhancer extends JwtAccessTokenConverter {

    Logger logger = LoggerFactory.getLogger(CustomTokenEnhancer.class);

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        logger.debug("Principal:"+authentication.getPrincipal());
        UserAuthenticated user = (UserAuthenticated) authentication.getPrincipal();
        OAuth2Request oauth2Request = authentication.getOAuth2Request();
        final Map<String, Object> additionalInformation = new HashMap<>();

        if (oauth2Request.getClientId().equalsIgnoreCase("web-react")){
            logger.debug("*********user:"+user.getUsername());
        }

        additionalInformation.put("customInfo",oauth2Request.getClientId());
        additionalInformation.put("authorities", user.getAuthorities());
        additionalInformation.put("dltAddress", (String)user.getUserInfo().get("dltAddress"));
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
        
        accessToken = super.enhance(accessToken, authentication);
        
        return accessToken;
    }
}
