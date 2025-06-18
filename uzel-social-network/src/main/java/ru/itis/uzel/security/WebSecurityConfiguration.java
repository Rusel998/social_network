package ru.itis.uzel.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.itis.uzel.entity.User;
import ru.itis.uzel.security.details.UserDetailsImpl;
import ru.itis.uzel.service.impl.GoogleOAuth2Service;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {

    private final GoogleOAuth2Service oAuth2Service;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        HttpSecurity httpSecurity = http
                .securityMatcher("/**")
                .authorizeHttpRequests(auth -> auth.requestMatchers("/", "/confirm/**", "/api/**",
                                "/images/**", "/js/**").permitAll()
                        .requestMatchers("/register").anonymous()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true")
                        .permitAll())
                .oauth2Login(oath2 -> {
                    oath2.loginPage("/login")
                            .successHandler((request, response, authentication) -> {
                                        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
                                        User user = oAuth2Service.saveOauth2User(token);
                                        UserDetailsImpl userDetails = new UserDetailsImpl(user);
                                        UsernamePasswordAuthenticationToken authToken =
                                                new UsernamePasswordAuthenticationToken(
                                                        userDetails,
                                                        null,
                                                        userDetails.getAuthorities()
                                                );
                                        SecurityContextHolder.getContext().setAuthentication(authToken);
                                        response.sendRedirect("/");
                                    }
                            );
                })
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/error/403")
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(
                                new AntPathRequestMatcher("/ws-chat/**")
                        )
                );

        return httpSecurity.build();
    }

}
