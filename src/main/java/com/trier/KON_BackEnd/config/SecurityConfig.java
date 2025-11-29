package com.trier.KON_BackEnd.config;

import com.trier.KON_BackEnd.dto.response.AuthResponseDTO;
import com.trier.KON_BackEnd.repository.UsuarioRepository;
import com.trier.KON_BackEnd.services.OAuth2Service;
import com.trier.KON_BackEnd.utils.JwtAuthFilter;
import com.trier.KON_BackEnd.utils.JwtUtil;
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

import jakarta.servlet.http.HttpServletResponse;
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

    @Value("${frontend.url:http://localhost:4200}")
    private String frontendUrl;

    @Autowired
    private JwtUtil jwtUtil;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        List<String> allowedOrigins = new java.util.ArrayList<>(Arrays.asList(
                "http://localhost:*",
                "https://localhost:*"
        ));
        if (frontendUrl != null && !frontendUrl.isEmpty()) {
            allowedOrigins.add(frontendUrl.replace("http://", "*://").replace("https://", "*://"));
        }
        configuration.setAllowedOriginPatterns(allowedOrigins);
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {

        JwtAuthFilter jwtAuthFilter = new JwtAuthFilter(userDetailsService, jwtUtil);

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/**",
                                "/api/oauth2/**",
                                "/oauth2/**",
                                "/login/oauth2/code/**",
                                "/api/usuario/cadastro",
                                "/api/plano/listar",
                                "/api/v1/categoria/listar/ativas",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()
                        .requestMatchers(
                                "/api/usuario/**",
                                "/api/role/**",
                                "/api/v1/categoria/cadastrar",
                                "/api/v1/categoria/atualizar/**",
                                "/api/v1/categoria/listar/todas",
                                "/api/plano/cadastro",
                                "/api/plano/atualizar/**",
                                "/api/sla/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                "/api/v1/chamado/atribuir/**",
                                "/api/v1/chamado/atualizar/status/**",
                                "/api/v1/chamado/fechar/**",
                                "/api/v1/chamado/listar/status/**",
                                "/api/v1/chamado/listar/responsavel/**",
                                "/api/v1/chamado/listar/nao-atribuidos",
                                "/api/v1/chamado/listar/todos"
                        ).hasAnyRole("ADMIN", "AGENTE")
                        .requestMatchers(
                                "/api/v1/chamado/abrir",
                                "/api/v1/chamado/listar/{cdChamado}",
                                "/api/v1/chamado/listar/solicitante/**",
                                "/api/v1/comentario/**",
                                "/api/v1/anexo/**"
                        ).hasAnyRole("ADMIN", "AGENTE", "USER")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((request, response, authException) -> {
                            if (request.getRequestURI().startsWith("/api/")) {
                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                response.setContentType("application/json;charset=UTF-8");
                                response.getWriter().write("{\"error\":\"Unauthorized\",\"message\":\"Token inválido ou ausente\"}");
                            } else {
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                            }
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType("application/json;charset=UTF-8");
                            response.getWriter().write("{\"error\":\"Forbidden\",\"message\":\"Acesso negado\"}");
                        })
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler((request, response, authentication) -> {
                            try {
                                OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
                                AuthResponseDTO authResponse = oAuth2Service.processOAuthPostLogin(oAuth2User);

                                String roles = authResponse.usuario().roles().stream()
                                        .collect(Collectors.joining(","));

                                String redirectUrl = UriComponentsBuilder.fromUriString(frontendUrl + "/oauth2/redirect")
                                        .queryParam("token", authResponse.token())
                                        .queryParam("userId", authResponse.usuario().cdUsuario())
                                        .queryParam("userName", URLEncoder.encode(authResponse.usuario().nmUsuario(), StandardCharsets.UTF_8))
                                        .queryParam("userEmail", URLEncoder.encode(authResponse.usuario().dsEmail(), StandardCharsets.UTF_8))
                                        .queryParam("userRoles", URLEncoder.encode(roles, StandardCharsets.UTF_8))
                                        .build().toUriString();

                                response.sendRedirect(redirectUrl);
                            } catch (Exception e) {
                                if ("USUARIO_SEM_NU_FUNCIONARIO".equals(e.getMessage()) ||
                                        "USUARIO_NAO_ENCONTRADO".equals(e.getMessage())) {

                                    OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
                                    String email = oAuth2User.getAttribute("email");
                                    String name = oAuth2User.getAttribute("name");

                                    String redirectUrl = UriComponentsBuilder.fromUriString(frontendUrl + "/oauth2/redirect")
                                            .queryParam("tempEmail", URLEncoder.encode(email, StandardCharsets.UTF_8))
                                            .queryParam("tempName", URLEncoder.encode(name, StandardCharsets.UTF_8))
                                            .build().toUriString();

                                    response.sendRedirect(redirectUrl);
                                } else {
                                    String errorUrl = frontendUrl + "/oauth2/redirect?error=" +
                                            URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
                                    response.sendRedirect(errorUrl);
                                }
                            }
                        })
                        .failureHandler((request, response, exception) -> {
                            String errorUrl = frontendUrl + "/oauth2/redirect?error=authentication_failed";
                            response.sendRedirect(errorUrl);
                        })
                );

        return http.build();
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
                                return new SimpleGrantedAuthority(nome != null && nome.startsWith("ROLE_") ? nome : "ROLE_" + nome);
                            })
                            .collect(Collectors.toList());

                    return new User(usuario.getDsEmail(), usuario.getDsSenha(), authorities);
                })
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + email));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
