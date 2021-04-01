package org.jhipster.amazinbookstore.config;

import static org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers.pathMatchers;

import org.jhipster.amazinbookstore.security.AuthoritiesConstants;
import org.jhipster.amazinbookstore.web.filter.SpaWebFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
import org.springframework.security.web.server.authentication.logout.HttpStatusReturningServerLogoutSuccessHandler;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.server.header.ReferrerPolicyServerHttpHeadersWriter;
import org.springframework.security.web.server.util.matcher.NegatedServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.OrServerWebExchangeMatcher;
import org.zalando.problem.spring.webflux.advice.security.SecurityProblemSupport;
import reactor.core.publisher.Mono;
import tech.jhipster.web.filter.reactive.CookieCsrfFilter;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Import(SecurityProblemSupport.class)
public class SecurityConfiguration {

    private final ReactiveUserDetailsService userDetailsService;

    private final SecurityProblemSupport problemSupport;

    public SecurityConfiguration(ReactiveUserDetailsService userDetailsService, SecurityProblemSupport problemSupport) {
        this.userDetailsService = userDetailsService;
        this.problemSupport = problemSupport;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager() {
        UserDetailsRepositoryReactiveAuthenticationManager authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(
            userDetailsService
        );
        authenticationManager.setPasswordEncoder(passwordEncoder());
        return authenticationManager;
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        // @formatter:off
        http
            .securityMatcher(new NegatedServerWebExchangeMatcher(new OrServerWebExchangeMatcher(
                pathMatchers("/app/**", "/i18n/**", "/content/**", "/swagger-ui/**", "/swagger-resources/**", "/v2/api-docs", "/v3/api-docs", "/test/**"),
                pathMatchers(HttpMethod.OPTIONS, "/**")
            )))
            .csrf()
                .csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse())
        .and()
            // See https://github.com/spring-projects/spring-security/issues/5766
            .addFilterAt(new CookieCsrfFilter(), SecurityWebFiltersOrder.REACTOR_CONTEXT)
            .addFilterAt(new SpaWebFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
            .authenticationManager(reactiveAuthenticationManager())
            .exceptionHandling()
                .accessDeniedHandler(problemSupport)
                .authenticationEntryPoint(problemSupport)
        .and()
            .formLogin()
            .requiresAuthenticationMatcher(pathMatchers(HttpMethod.POST, "/api/authentication"))
            .authenticationEntryPoint(new HttpStatusServerEntryPoint(HttpStatus.OK))
            .authenticationSuccessHandler(this::onAuthenticationSuccess)
            .authenticationFailureHandler(this::onAuthenticationError)
        .and()
            .logout()
            .logoutUrl("/api/logout")
            .logoutSuccessHandler(new HttpStatusReturningServerLogoutSuccessHandler())
        .and()
            .headers()
                .contentSecurityPolicy("default-src 'self'; frame-src 'self' data:; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com https://www.google-analytics.com; style-src 'self' https://fonts.googleapis.com 'unsafe-inline'; img-src 'self' data: https://www.google-analytics.com; font-src 'self' https://fonts.gstatic.com data:")
            .and()
                .referrerPolicy(ReferrerPolicyServerHttpHeadersWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
            .and()
                .featurePolicy("geolocation 'none'; midi 'none'; sync-xhr 'none'; microphone 'none'; camera 'none'; magnetometer 'none'; gyroscope 'none'; fullscreen 'self'; payment 'none'")
            .and()
                .frameOptions().disable()
        .and()
            .authorizeExchange()
            .pathMatchers("/").permitAll()
            .pathMatchers("/*.*").permitAll()
            .pathMatchers("/api/register").permitAll()
            .pathMatchers("/api/activate").permitAll()
            .pathMatchers("/api/authenticate").permitAll()
            .pathMatchers("/api/account/reset-password/init").permitAll()
            .pathMatchers("/api/account/reset-password/finish").permitAll()
            .pathMatchers("/api/auth-info").permitAll()
            .pathMatchers("/api/admin/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .pathMatchers("/api/**").authenticated()
            .pathMatchers("/services/**").authenticated()
            .pathMatchers("/management/health").permitAll()
            .pathMatchers("/management/health/**").permitAll()
            .pathMatchers("/management/info").permitAll()
            .pathMatchers("/management/prometheus").permitAll()
            .pathMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN);
        // @formatter:on
        return http.build();
    }

    private Mono<Void> onAuthenticationError(WebFilterExchange exchange, AuthenticationException e) {
        exchange.getExchange().getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return Mono.empty();
    }

    private Mono<Void> onAuthenticationSuccess(WebFilterExchange exchange, Authentication authentication) {
        exchange.getExchange().getResponse().setStatusCode(HttpStatus.OK);
        return Mono.empty();
    }
}
