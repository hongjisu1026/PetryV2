package com.dido.petry.config;

import com.dido.petry.handler.CustomAuthFailureHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
//    private final AuthenticationFailureHandler failureHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").authenticated()
                .anyRequest().permitAll();
        http
                .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/auth/login-process")
                    .defaultSuccessUrl("/")
                    .failureHandler(new CustomAuthFailureHandler())
                    .usernameParameter("username")
                    .passwordParameter("password")
                .and()
                    .logout()
                    .logoutUrl("/auth/logout-process")
                    .logoutSuccessUrl("/login")
                    .invalidateHttpSession(true)
                .and()
                    .csrf().disable();

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
    /*
    http
            .authorizeRequests()
            .antMatchers("/login", "/register").permitAll()
                        .antMatchers("/").hasRole("USER")
                        .antMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated();
                        */
}
