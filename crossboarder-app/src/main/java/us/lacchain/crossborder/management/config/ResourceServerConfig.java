package us.lacchain.crossborder.management.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.http.HttpMethod;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .antMatcher("/api/**")
//            .csrf().disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/api/user").permitAll()
            .antMatchers(HttpMethod.GET, "/api/user/forgot/**").permitAll()
            .antMatchers(HttpMethod.POST, "/api/user/forgot/**").permitAll()
            .antMatchers(HttpMethod.POST, "/api/user/reset").permitAll()
            .antMatchers(HttpMethod.POST, "/api/event").permitAll()
            .antMatchers(HttpMethod.POST, "/api/block").permitAll()
            .antMatchers("/api/**").access("#oauth2.hasScope('write')")
            .anyRequest().denyAll()
            .and()
            .logout()
            .logoutUrl("/logout")
            .permitAll();
    }
}
