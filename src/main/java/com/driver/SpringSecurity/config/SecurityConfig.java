package com.driver.SpringSecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public UserDetailsService getUserDetailsService(){

        UserDetails student = User.withUsername("bharat ")
                .password(getPasswordEncoder().encode("patel123"))
                .roles("STUDENT")
                .build();

        UserDetails student1 = User.withUsername("shakshi")
                .password(getPasswordEncoder().encode("shakshi"))
                .roles("STUDENT")
                .build();

        UserDetails admin = User.withUsername("riya")
                .password(getPasswordEncoder().encode("riya123"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(student,admin,student1);
    }
    @SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/public/**")
                .permitAll()
                .requestMatchers("/student/**")
                .hasAnyRole("STUDENT", "ADMIN")
                .requestMatchers("/admin/**")
                .hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin();

        return httpSecurity.build();
    }

}
