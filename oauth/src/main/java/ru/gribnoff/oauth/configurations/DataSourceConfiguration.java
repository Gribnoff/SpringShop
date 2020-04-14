package ru.gribnoff.oauth.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class DataSourceConfiguration {

    private final DataSource dataSource;

    @Bean
    public JdbcTokenStore getTokenStore() {
        return new JdbcTokenStore(dataSource);
    }

}