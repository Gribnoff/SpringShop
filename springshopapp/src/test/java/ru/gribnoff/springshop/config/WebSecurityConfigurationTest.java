package ru.gribnoff.springshop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@Configuration
public class WebSecurityConfigurationTest extends WebSecurityConfigurerAdapter {
//	@Value("${constants.test_root_path}")
//	private String TEST_ROOT_PATH;
//
//	@Override
//	public void configure(WebSecurity web) {
//		web.ignoring()
//				.antMatchers(TEST_ROOT_PATH + "/admin")
//				.antMatchers(TEST_ROOT_PATH + "/products"); // указать тут URL который проверяете
//	}
}
