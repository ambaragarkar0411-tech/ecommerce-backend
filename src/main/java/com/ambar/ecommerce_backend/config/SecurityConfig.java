package com.ambar.ecommerce_backend.config;

import com.ambar.ecommerce_backend.security.JwtFilter;
import com.ambar.ecommerce_backend.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    //password encoding in security config
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {

//        csrf().disable()
//        disables CSRF (needed for APIs)
//🔹 .requestMatchers("/auth/**").permitAll()
//        register & login are public
//🔹 .anyRequest().authenticated()
//        all other APIs require login
//🔹 .addFilterBefore(...)
//        VERY IMPORTANT
//        Adds your JWT filter before Spring security

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                                //for swagger
                                .requestMatchers(
                                        "/auth/**",
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html"
                                ).permitAll()
                        //.requestMatchers("/auth/**").permitAll() // ✅ same as before


                        //role based rules
//                        If something fails
//                        🔴 403 Forbidden
//                        👉 Role issue
//                        🔴 401 Unauthorized
//                        👉 Token missing/invalid
                        .requestMatchers(HttpMethod.GET,"/api/products/**")
                        .hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/products/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/products/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/products/**")
                        .hasRole("ADMIN")
                        .anyRequest().authenticated()            // ✅ same as before
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


}