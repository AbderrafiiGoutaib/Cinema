package org.sid.Cinema.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = passwordEncoder();
        System.out.println("****************");
        System.out.println(passwordEncoder.encode("hello"));
        System.out.println(passwordEncoder.encode("hello10"));
        System.out.println("****************");
        auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery("select UserName as principal  , Password as credentials , active from users where UserName = ?")
         .authoritiesByUsernameQuery("select UserName as principal , role as role from users_roles where UserName = ?")
                .passwordEncoder(passwordEncoder).rolePrefix("ROLE_");

       
    }
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login");
        //http.formLogin();
        http.authorizeRequests().antMatchers("/ajouter**/**","/delete**/**","/form**/**").hasRole("ADMIN");
        http.authorizeRequests().antMatchers("/cinema/**","/film/**").hasRole("USER");
        http.authorizeRequests().antMatchers("/index","/login","/webjars/**").permitAll();
         http.authorizeRequests().anyRequest().authenticated(); //every resource need authentication

        //http.csrf();
        http.exceptionHandling().accessDeniedPage("/erreur");
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
