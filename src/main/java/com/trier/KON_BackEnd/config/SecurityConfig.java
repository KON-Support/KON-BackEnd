package com.trier.KON_BackEnd.config;

import com.trier.KON_BackEnd.dto.response.AuthResponseDTO;
import com.trier.KON_BackEnd.repository.UsuarioRepository;
import com.trier.KON_BackEnd.services.OAuth2Service;
import com.trier.KON_BackEnd.utils.JwtAuthFilter;
import com.trier.KON_BackEnd.utils.JwtUtil;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private OAuth2Service oAuth2Service;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, UserDetailsService userDetailsService,
                                           ObjectProvider<JwtUtil> jwtUtilProvider) throws Exception {

        JwtUtil jwtUtil = jwtUtilProvider.getIfAvailable();
        JwtAuthFilter jwtAuthFilter = new JwtAuthFilter(userDetailsService, jwtUtil);

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/oauth2/**").permitAll()
                        .requestMatchers("/login/oauth2/code/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .anyRequest().permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler((request, response, authentication) -> {
                            try {
                                OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
                                AuthResponseDTO authResponse = oAuth2Service.processOAuthPostLogin(oAuth2User);

                                String redirectUrl = UriComponentsBuilder.fromUriString(frontendUrl + "/oauth2/redirect")
                                        .queryParam("token", authResponse.token())
                                        .queryParam("userId", authResponse.usuario().cdUsuario())
                                        .queryParam("userName", URLEncoder.encode(authResponse.usuario().nmUsuario(), StandardCharsets.UTF_8))
                                        .queryParam("userEmail", URLEncoder.encode(authResponse.usuario().dsEmail(), StandardCharsets.UTF_8))
                                        .build()
                                        .toUriString();

                                response.sendRedirect(redirectUrl);
                            } catch (Exception e) {
                                String errorUrl = frontendUrl + "/oauth2/redirect?error=" +
                                        URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
                                response.sendRedirect(errorUrl);
                            }
                        })
                        .failureHandler((request, response, exception) -> {
                            String errorUrl = frontendUrl + "/oauth2/redirect?error=authentication_failed";
                            response.sendRedirect(errorUrl);
                        })
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(Arrays.asList("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> usuarioRepository.findByDsEmail(email)
                .map(usuario -> {
                    List<GrantedAuthority> authorities = usuario.getRoleModel().stream()
                            .map(role -> {
                                String nome = role.getNmRole();
                                String withPrefix = nome != null && nome.startsWith("ROLE_") ? nome : "ROLE_" + nome;
                                return new SimpleGrantedAuthority(withPrefix);
                            })
                            .collect(Collectors.toList());

                    return new User(
                            usuario.getDsEmail(),
                            usuario.getDsSenha(),
                            authorities
                    );
                })
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + email));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}