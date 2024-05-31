
package com.project.shopapp.security;

import com.project.shopapp.DTO.EmailDTO;
import com.project.shopapp.DTO.GitDTO;
import com.project.shopapp.Service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import com.project.shopapp.Service.GithubService;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebMvc
@RequiredArgsConstructor
public class WebSecurityConfig {

        private final JwtTokenFilter jwtTokenFilter;

        private final EmailService EmailService;

        private final GithubService GithubService;

        @Value("${api.prefix}")
        private String apiPrefix;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(AbstractHttpConfigurer::disable)
                                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                                .authorizeHttpRequests(requests -> {
                                        requests
                                                        // ------------------------users--------------------//
                                                        // vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv//

                                                        .requestMatchers(
                                                                        String.format("%s/users/register", apiPrefix),
                                                                        String.format("%s/users/login", apiPrefix),
                                                                        String.format("%s/users/login/oauth2**",
                                                                                        apiPrefix))
                                                        .permitAll()

                                                        .requestMatchers(PUT,
                                                                        String.format("%s/users/update/pass/**",
                                                                                        apiPrefix))
                                                        .permitAll()
                                                        .requestMatchers(PUT,
                                                                        String.format("%s/users/update/**",
                                                                                        apiPrefix))
                                                        .permitAll()

                                                        .requestMatchers(POST,
                                                                        String.format("%s/users/checkactive",
                                                                                        apiPrefix))
                                                        .permitAll()

                                                        .requestMatchers(GET,
                                                                        String.format("%s/users**", apiPrefix))
                                                        .permitAll()
                                                        .requestMatchers(GET,
                                                                        String.format("%s/users/**", apiPrefix))
                                                        .permitAll()
                                                        .requestMatchers(GET,
                                                                        String.format("%s/users/**", apiPrefix))
                                                        .permitAll()
                                                        .requestMatchers(POST,
                                                                        String.format("%s/users/**", apiPrefix))
                                                        .permitAll()
                                                        .requestMatchers(POST,
                                                                        String.format("%s/users**", apiPrefix))
                                                        .permitAll()

                                                        .requestMatchers(DELETE,
                                                                        String.format("%s/users/**", apiPrefix))
                                                        .permitAll()

                                                        .requestMatchers(String
                                                                        .format("%s/oauth2/login/google", apiPrefix))
                                                        .permitAll()
                                                        .requestMatchers(String.format("%s/oauth2/login/facebook",
                                                                        apiPrefix))
                                                        .permitAll()

                                                        .requestMatchers(String.format("%s/emails/users/**",
                                                                        apiPrefix))
                                                        .permitAll()
                                                        .requestMatchers(String.format("%s/emails/users", apiPrefix))
                                                        .permitAll()

                                                        .requestMatchers(String.format("%s/githubs/users/**",
                                                                        apiPrefix))
                                                        .permitAll()
                                                        .requestMatchers(String.format("%s/githubs/users", apiPrefix))
                                                        .permitAll()

                                                        .requestMatchers(GET, String.format("%s/githubs/users/**",
                                                                        apiPrefix))
                                                        .permitAll()
                                                        .requestMatchers(GET,
                                                                        String.format("%s/githubs/users", apiPrefix))
                                                        .permitAll()

                                                        .requestMatchers(GET,
                                                                        String.format("%s/emails/users/**", apiPrefix))
                                                        .permitAll()

                                                        .requestMatchers(GET,
                                                                        String.format("%s/emails/users", apiPrefix))
                                                        .permitAll()

                                                        .requestMatchers(GET,
                                                                        String.format("%s/users/email/**", apiPrefix))
                                                        .permitAll()
                                                        .requestMatchers(POST,
                                                                        String.format("%s/users/**", apiPrefix))
                                                        .permitAll()

                                                        .anyRequest().authenticated();

                                })
                                .csrf(AbstractHttpConfigurer::disable)
                                .oauth2Login(Customizer.withDefaults());
                http.cors(Customizer.withDefaults());
                http.oauth2Login(oauth2 -> oauth2.successHandler(authenticationSuccessHandler()));
                http.cors(new Customizer<CorsConfigurer<HttpSecurity>>() {
                        @Override
                        public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
                                CorsConfiguration configuration = new CorsConfiguration();
                                configuration.setAllowedOrigins(List.of("*"));
                                configuration.setAllowedMethods(
                                                Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                                configuration.setAllowedHeaders(Arrays.asList("Origin", "Authorization",
                                                "content-type",
                                                "x-auth-token"));
                                configuration.setExposedHeaders(List.of("x-auth-token"));
                                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                                source.registerCorsConfiguration("/**", configuration);
                                httpSecurityCorsConfigurer.configurationSource(source);
                        }
                });
                return http.build();
        }

        @Bean
        public AuthenticationSuccessHandler authenticationSuccessHandler() {
                return (request, response, authentication) -> {
                        Long id = 0L;
                        String type = "";

                        Object principal = authentication.getPrincipal();

                        if (principal instanceof OidcUser) {
                                OidcUser oidcUser = (OidcUser) principal;
                                String name = oidcUser.getFullName();
                                String email = oidcUser.getEmail();
                                String picture = oidcUser.getPicture();
                                EmailService.createUser(EmailDTO.builder()
                                                .email(email)
                                                .name(name)
                                                .picture(picture)
                                                .build());

                                id = this.EmailService.getUserByEmail(email).getId();
                                type = "email";
                        } else {
                                if (authentication.getPrincipal() instanceof OAuth2User) {
                                        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
                                        String name = oauth2User.getAttribute("name");
                                        String email = oauth2User.getAttribute("email");
                                        Integer idg = oauth2User.getAttribute("id");
                                        String idgit = idg + "";
                                        type = "github";

                                        GithubService.createUser(GitDTO.builder()
                                                        .githubId(idgit)
                                                        .email(email)
                                                        .name(name)
                                                        .build());
                                        id = this.GithubService.getGithubByEmail(email).getId();
                                }
                        }

                        if (id != 0) {
                                response.sendRedirect("http://localhost:4200/onesound/home/users/update?id="
                                                + id
                                                + "&type=" + type);

                        } else {
                                response.sendRedirect("http://localhost:4200");

                        }
                };
        }

}
