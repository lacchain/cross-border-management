package us.lacchain.crossborder.management.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import us.lacchain.crossborder.management.service.CustomTokenEnhancer;

import java.time.Duration;

/**
 * @author Adrian Pareja
 */

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Value("${crossborder.oauth.client.web.client-id}")
    private String clientId;

    @Value("${crossborder.oauth.client.web.client-secret}")
    private String clientSecret;

    @Value("${crossborder.oauth.client.web.access-token-time}")
    private Integer accessTokenTime;

    @Value("${crossborder.oauth.client.web.refresh-token-time}")
    private Integer refreshTokenTime;

    @Value("${crossborder.oauth.client.web.private-key}")
    private String privateKey;

    @Value("${crossborder.oauth.client.web.public-key}")
    private String publicKey;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory()
                .withClient(clientId)
                .secret(passwordEncoder.encode(clientSecret))
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("read","write")
                .accessTokenValiditySeconds((int) Duration.ofMinutes(accessTokenTime).getSeconds())
                .refreshTokenValiditySeconds((int) Duration.ofMinutes(refreshTokenTime).getSeconds());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .authenticationManager(authenticationManager)
                .tokenStore(tokenStore)
                .accessTokenConverter(tokenEnhancer())
                .tokenServices(tokenServices(authenticationManager, endpoints.getClientDetailsService(), tokenStore));
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()")
                   .checkTokenAccess("isAuthenticated()");
    }

    @Autowired
    @Primary
    @Bean
    public DefaultTokenServices tokenServices(AuthenticationManager authenticationManager, ClientDetailsService clientDetailsService, TokenStore tokenStore) {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore);
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setReuseRefreshToken(true);
        tokenServices.setClientDetailsService(clientDetailsService);
        tokenServices.setAuthenticationManager(authenticationManager);
        tokenServices.setTokenEnhancer(tokenEnhancer());
        return tokenServices;
    }

   @Bean
   public JwtAccessTokenConverter tokenEnhancer() {
      JwtAccessTokenConverter converter = new CustomTokenEnhancer();
      converter.setSigningKey(privateKey);
      converter.setVerifierKey(publicKey);
      return converter;
   }
}
