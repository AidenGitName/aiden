package org.zerock.sequrity;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Log
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("security config.............");

        // antMatcherss은 특정 경로 지정
        http.authorizeRequests().antMatchers("/guest/**").permitAll();

        // 권한 경로 제한
        http.authorizeRequests().antMatchers("/manager/**").hasRole("MANAGER");

        http.authorizeRequests().antMatchers("/admin").hasRole("ADMIN");


        http.formLogin().loginPage("/login");

        http.exceptionHandling().accessDeniedPage("/accessDenied");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        log.info("build Auth global.............");
        auth.inMemoryAuthentication().withUser("manager").password("{noop}1111").roles("MANAGER");
    }
}
