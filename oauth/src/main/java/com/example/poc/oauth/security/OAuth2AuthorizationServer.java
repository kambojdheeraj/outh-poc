package com.example.poc.oauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;


/*
 * Second step where AuthServer will give token through redirectURI after verifying credentials 
 * Then using code, client can ask for access token from auth server
 */

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServer extends AuthorizationServerConfigurerAdapter 
{
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
 
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
            .tokenKeyAccess("permitAll()")
            .checkTokenAccess("isAuthenticated()")
            .allowFormAuthenticationForClients();
    }
 
    /*
     * The Authorization Code grant type is used by confidential and public clients to exchange an authorization code for an access token.
		After the user returns to the client via the redirect URL, the application will get the authorization code from the URL and use it to request an access token.
     */
    
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
            .inMemory()
            .withClient("clientapp").secret(passwordEncoder.encode("123456"))
            .authorizedGrantTypes("password", "authorization_code", "refresh_token")
            .authorities("READ_ONLY_CLIENT")
            .scopes("read_profile_info")
            .resourceIds("oauth2-resource")
            .redirectUris("http://localhost:8081/login")
            .accessTokenValiditySeconds(120)
            .refreshTokenValiditySeconds(240000);
    }
}