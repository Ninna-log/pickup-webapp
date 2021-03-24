package com.pickupapp.pickupapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import com.pickupapp.pickupapp.repositories.CustomerRepository;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomerRepository customerRepository;

    // Spring Security provides an interface, PasswordEncoder,
    // to do this job. We can get an instance of an encoder using another class, PasswordEncoderFactories
    // Password is been encrypted before storing it
    @Bean
    public PasswordEncoder passwordEncoder() {

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // Provides the code that Spring should run to get a user's password and roles
    //Giving Spring a method to search in playerRepository the userName and password of player and authenticate it.
    //AUTORIZATION
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.headers().frameOptions().disable(); //<--- Para que se pueda ver la BD /h2-console
        http.authorizeRequests()
                .antMatchers("/web/pickup.html").hasAnyAuthority("USER")
                .antMatchers("/web/**", "/api/**").permitAll()
                .antMatchers("/rest/**").hasAnyAuthority("user")
                .anyRequest().permitAll();

        http.formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage("/api/v1/login");

        http.logout().logoutUrl("/api/v1/logout");

        // turn off checking for CSRF tokens
        http.csrf().disable();

        // if user is not authenticated, just send an authentication failure response
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }

    }
}