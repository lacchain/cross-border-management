package us.lacchain.crossborder.management.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import us.lacchain.crossborder.management.model.UserAuthenticated;

public class CustomTokenEnhancer extends JwtAccessTokenConverter {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        System.out.println("principal:"+authentication.getPrincipal());
        UserAuthenticated user = (UserAuthenticated) authentication.getPrincipal();
        OAuth2Request oauth2Request = authentication.getOAuth2Request();
        final Map<String, Object> additionalInformation = new HashMap<>();

        if (oauth2Request.getClientId().equalsIgnoreCase("web-react")){
            System.out.println("*********user:"+user.getUsername());
        }

        additionalInformation.put("customInfo",oauth2Request.getClientId());
        additionalInformation.put("authorities", user.getAuthorities());
        additionalInformation.put("dltAddress", (String)user.getUserInfo().get("dltAddress"));
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
        
        accessToken = super.enhance(accessToken, authentication);
        
        return accessToken;
    }
}
