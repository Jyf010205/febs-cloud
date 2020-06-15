package com.sgzs.febs.auth.config;

import com.sgzs.febs.auth.properties.FebsAuthProperties;
import com.sgzs.febs.auth.properties.FebsClientsProperties;
import com.sgzs.febs.auth.service.FebsUserDetailService;
import com.sgzs.febs.auth.translator.FebsWebResponseExceptionTranslator;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author: jianyufeng
 * @description: @EnableAuthorizationServer接管路径/oauth/token oauth2.0验证
 *               认证方式：clients.inMemory()
 * @date: 2020/5/28 16:50
 */
@Configuration
@EnableAuthorizationServer
public class FebsAuthorizationServerConfigure extends AuthorizationServerConfigurerAdapter {
    /**
     * FebsSecurityConfigure注入的BeanAuthenticationManager、PasswordEncoder
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    private FebsUserDetailService userDetailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private FebsAuthProperties authProperties;
    @Autowired
    private FebsWebResponseExceptionTranslator exceptionTranslator;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        FebsClientsProperties[] clientsArray = authProperties.getClients();
        /**
         * 1.客户端从认证服务器获取令牌的时候，必须使用client_id为febs，client_secret为123456的标识来获取；
         * 2.该client_id支持password模式获取令牌，并且可以通过refresh_token来获取新的令牌；
         * 3.在获取client_id为febs的令牌的时候，scope只能指定为all，否则将获取失败；
         * 4。请求头Authorization   Basic client_id:client_secret
         */
//        clients.inMemory()
//                .withClient("febs")
//                .secret(passwordEncoder.encode("123456"))
//                .authorizedGrantTypes("password","refresh_token")
//                .scopes("all");
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        if (ArrayUtils.isNotEmpty(clientsArray)) {
            for (FebsClientsProperties client : clientsArray) {
                if (StringUtils.isBlank(client.getClient())) {
                    throw new Exception("client不能为空");
                }
                if (StringUtils.isBlank(client.getSecret())) {
                    throw new Exception("secret不能为空");
                }
                String[] grantTypes = StringUtils.splitByWholeSeparatorPreserveAllTokens(client.getGrantType(), ",");
                builder.withClient(client.getClient())
                        .secret(passwordEncoder.encode(client.getSecret()))
                        .authorizedGrantTypes(grantTypes)
                        .scopes(client.getScope());
            }
        }
    }

    /**
     * 认证异常翻译器生效
     * @param endpoints
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(tokenStore())
                .userDetailsService(userDetailService)
                .authenticationManager(authenticationManager)
                .tokenServices(defaultTokenServices())
                .exceptionTranslator(exceptionTranslator);
    }

    @Bean
    public TokenStore tokenStore(){
        return new RedisTokenStore(redisConnectionFactory);
    }

    @Primary
    @Bean
    public DefaultTokenServices defaultTokenServices(){
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setSupportRefreshToken(true);
        //令牌有效时间
        tokenServices.setAccessTokenValiditySeconds(authProperties.getAccessTokenValiditySeconds());
        //刷新令牌有效时间
        tokenServices.setRefreshTokenValiditySeconds(authProperties.getRefreshTokenValiditySeconds());
        return tokenServices;
    }
}
