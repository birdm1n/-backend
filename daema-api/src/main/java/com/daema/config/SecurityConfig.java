package com.daema.config;

import com.daema.common.filter.JwtRequestFilter;
import com.daema.common.handler.CustomAccessDeniedHandler;
import com.daema.common.handler.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtRequestFilter jwtRequestFilter;

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter, CustomAuthenticationEntryPoint customAuthenticationEntryPoint, CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.httpBasic().disable()
                .authorizeRequests()
                    .antMatchers("/user/signup").permitAll()
                    .antMatchers("/user/login").permitAll()
                    .antMatchers("/user/invalidate").permitAll()
                    .antMatchers("/user/verify/**").permitAll()
                    .antMatchers("/dataHandle/existsData").permitAll()
                    .antMatchers("/**/joinStore").permitAll()
                    .antMatchers("/oauth/**").permitAll()
                    //.antMatchers("/dataHandle/extractApiFunc").permitAll()
                    .anyRequest().authenticated()
                    //.anyRequest().permitAll()
            .and()
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource())
            .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .exceptionHandling()
                    .authenticationEntryPoint(customAuthenticationEntryPoint)
                    .accessDeniedHandler(customAccessDeniedHandler)
            .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override // ignore check swagger resource
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        String profile = System.getProperty("spring.profiles.active");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowCredentials(true);
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT"));

        if(profile != null &&
                !"prod".equals(profile)) {

            configuration.addAllowedOrigin("*");
            configuration.addAllowedHeader("*");

        }else{
            List<String> allowedOrigins = new ArrayList<>();
            allowedOrigins.add("http://192.168.0.68:8080");
            allowedOrigins.add("http://192.168.0.14:8080");

            configuration.addAllowedOrigin("*");
            //configuration.setAllowedOrigins(allowedOrigins);

            configuration.addAllowedHeader("*");
            //configuration.setAllowedHeaders(Arrays.asList("X-Requested-With","Origin","Content-Type","Accept","Authorization"));

            //configuration.addExposedHeader("Authorization");
            //configuration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Headers", "Authorization, x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers"));
        }

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}