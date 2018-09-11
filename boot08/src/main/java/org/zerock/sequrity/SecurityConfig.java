package org.zerock.sequrity;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Log
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("security config.............");

        // antMatcherss은 특정 경로 지정
        http.authorizeRequests().antMatchers("/guest/**").permitAll();

        // 권한 경로 제한
        http.authorizeRequests().antMatchers("/manager/**").hasRole("MANAGER");

        http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");

        //로그인 페이지 보여주기
        http.formLogin().loginPage("/login");

        // 권한 없는 페이지 핸들링
        http.exceptionHandling().accessDeniedPage("/accessDenied");

        // 세션 무효화
        http.logout().logoutUrl("/logout").invalidateHttpSession(true);

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        log.info("build Auth global.............");

//        auth.inMemoryAuthentication().
//                withUser("manager").
//                password("{noop}1111").     //패스워드 암호화 적용 X
//                roles("MANAGER");

        String query1 = "SELECT uid username, CONCAT('{noop}',upw) password, true enabled FROM tbl_membersq WHERE uid=?";

        String query2 = "SELECT member uid, role_name role FROM tbl_member_roles WHERE member = ?";

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(query1)
                .rolePrefix("ROLE_")
                .authoritiesByUsernameQuery(query2);
    }
}
