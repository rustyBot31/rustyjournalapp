package com.rustyleague.rustyjournal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.rustyleague.rustyjournal.filter.jwtFilter;
import com.rustyleague.rustyjournal.service.userDetails;
import com.rustyleague.rustyjournal.service.userDetails;
import com.rustyleague.rustyjournal.service.userSevice;

import jakarta.servlet.Filter;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;

@Configuration
@EnableWebSecurity
public class springsecurity {

    @Autowired
    public userDetails userservice;

    @Autowired
    public jwtFilter jwtFilter;

    @Bean
    public AuthenticationManager authenticationManger() {
            DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
            authenticationProvider.setUserDetailsService(userservice);
            authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());

            return new ProviderManager(authenticationProvider);
        }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authz)->authz
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .requestMatchers("/journal/**","/users/**").authenticated()
            .anyRequest().permitAll());
            //.formLogin(withDefaults())
            http.csrf((csrf)->csrf.disable());
            http.sessionManagement((session)->session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
            http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
