package us.lacchain.crossborder.management.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.dao.DataAccessResourceFailureException; 
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import org.springframework.stereotype.Component;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import us.lacchain.crossborder.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import us.lacchain.crossborder.management.model.UserAuthenticated;
import us.lacchain.crossborder.management.model.UserLogin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    @Autowired
    UserRepository userRepository;

    public CustomAuthenticationProvider() {
        super();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException, DataAccessResourceFailureException {
        try {
            final String username = authentication.getName();
            final String password = authentication.getCredentials().toString();

            UserLogin user = userRepository.getUserLogin(username,password);

            if (user == null){
                throw new BadCredentialsException("User or password is incorrect");    
            }

            logger.debug("Authenticate username:"+user.getEmail());
            logger.debug("Authenticate dltAddress:"+user.getDltAddress());

            final List<GrantedAuthority> grantedAuths = new ArrayList<>();
            grantedAuths.add(new SimpleGrantedAuthority(user.getRole()));
            
            Map<String,Object> additionalInfo = new HashMap<>();
            additionalInfo.put("email",user.getEmail());
            additionalInfo.put("dltAddress",user.getDltAddress());
        
            final User userLogged = new User(username, (String) authentication.getCredentials(), grantedAuths);
            final UserAuthenticated userAuth = new UserAuthenticated(userLogged, additionalInfo);

            return new UsernamePasswordAuthenticationToken(userAuth, password, grantedAuths);
        } catch (DataAccessResourceFailureException e) {
            logger.info("Error connecting to database");
            logger.error(e.getMessage());
            throw e;
        }

        //return null;
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}