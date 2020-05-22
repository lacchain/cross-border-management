package us.lacchain.crossborder.management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class SessionRegulator {

  @Autowired
  private TokenStore tokenStore;

  @Value("${crossborder.oauth.client.web.client-id}")
  private String clientId;

  public boolean findAndRemoveOldTokens(String userName) {
    AtomicBoolean isRevoked = new AtomicBoolean(false);
    Collection<OAuth2AccessToken> accessTokenList = tokenStore.findTokensByClientIdAndUserName(clientId, userName);
    accessTokenList.forEach(token -> {
      tokenStore.removeRefreshToken(token.getRefreshToken());
      tokenStore.removeAccessToken(token);
      isRevoked.set(true);
    });
    return isRevoked.get();
  }
}
