package com.pickupapp.pickupapp.config;

import com.pickupapp.pickupapp.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.pickupapp.pickupapp.repositories.CustomerRepository;

@Configuration
public class WebUserConfig extends GlobalAuthenticationConfigurerAdapter {
    //AUTHENTICATION
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userName -> {
            Customer customer = customerRepository.findByUserName(userName);
            if (customer != null) {
                return new User(customer.getUserName(), customer.getPassword(),
                        AuthorityUtils.createAuthorityList("USER")); // <-- User role
            } else {
                throw new UsernameNotFoundException("Unknown user: " + userName);
            }
        });
    }
}

