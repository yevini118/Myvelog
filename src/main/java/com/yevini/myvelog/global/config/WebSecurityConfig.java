package com.yevini.myvelog.global.config;

import com.yevini.myvelog.global.security.CustomAuthenticationSuccessHandler;
import com.yevini.myvelog.global.security.VelogAuthenticationFilter;
import com.yevini.myvelog.global.security.VelogAuthenticationProvider;
import com.yevini.myvelog.global.util.JwtUtil;
import com.yevini.myvelog.global.util.redis.UserRedisUtil;
import com.yevini.myvelog.web.service.SeleniumService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig{

    private final SeleniumService seleniumService;
    private final JwtUtil jwtUtil;
    private final UserRedisUtil userRedisUtil;

    private final VelogAuthenticationProvider velogAuthenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .formLogin().disable()
                .httpBasic().disable()
                .anonymous().disable()

                .authorizeHttpRequests()
                .anyRequest().permitAll()
                .and()
                .addFilterBefore(velogAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//                .and()
//                .securityContext()
//                .securityContextRepository(new HttpSessionSecurityContextRepository());

        return http.build();
    }

    @Bean VelogAuthenticationFilter velogAuthenticationFilter() {
        VelogAuthenticationFilter filter = new VelogAuthenticationFilter("/login", seleniumService, jwtUtil, userRedisUtil);
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(new CustomAuthenticationSuccessHandler("/main/"));
        return filter;
    }

    @Bean
    public AuthenticationManager authenticationManager() {

        return new ProviderManager(Arrays.asList(velogAuthenticationProvider));
    }

}
