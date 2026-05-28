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
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;


@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5179"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

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
                .cors(cors -> {})
               .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // PUBLIC APIs
                        .requestMatchers(
                                "/auth/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/uploads/**"
                        ).permitAll()

                        // PRODUCT APIs
                        .requestMatchers(HttpMethod.GET, "/api/products/**")
                        .hasAnyRole("USER", "ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/products/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/api/products/**")
                                .permitAll()
                        //.hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/api/products/**")
                        .hasRole("ADMIN")

                        .requestMatchers("/cart/**").authenticated()

                        .requestMatchers("/wishlist/**").permitAll()

                        .anyRequest().authenticated()

//                .authorizeHttpRequests(auth -> auth
//
//                                //for swagger
//                                .requestMatchers(
//                                        "/auth/**",
//                                        "/v3/api-docs/**",
//                                        "/swagger-ui/**",
//                                        "/swagger-ui.html"
//                                ).permitAll()
//                        //.requestMatchers("/auth/**").permitAll() // ✅ same as before
//
////                                .requestMatchers(
////                                        "/api/products/category/**"
////                                ).permitAll()
//
//                        //role based rules
////                        If something fails
////                        🔴 403 Forbidden
////                        👉 Role issue
////                        🔴 401 Unauthorized
////                        👉 Token missing/invalid
//                        .requestMatchers(HttpMethod.GET,"/api/products/**")
//                        .hasAnyRole("USER","ADMIN")
//                        .requestMatchers(HttpMethod.POST,"/api/products/**")
//
//
//                        .hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.PUT,"/api/products/**")
//                                .hasRole("ADMIN")
//                                //.requestMatchers(HttpMethod.DELETE,"/api/products/**").permitAll()
//                       .requestMatchers(HttpMethod.DELETE,"/api/products/**")
//
//                       .hasRole("ADMIN")
//                                .requestMatchers("/cart/**").authenticated()
//                                .requestMatchers("/wishlist/**").permitAll()
//
//                                .requestMatchers(
//                                        "/auth/**",
//                                        "/api/products/**",
//                                        "/uploads/**"
//                                ).permitAll()
//
//
//                        .anyRequest().authenticated()            // ✅ same as before
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