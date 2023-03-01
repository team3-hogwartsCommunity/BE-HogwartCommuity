package com.sparta.spartaminiproject.security.config;

import com.sparta.spartaminiproject.security.filter.CustomAccessDeniedHandler;
import com.sparta.spartaminiproject.security.filter.CustomAuthenticationEntryPoint;
import com.sparta.spartaminiproject.security.jwt.JwtAuthFilter;
import com.sparta.spartaminiproject.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
public class WebSecurityConfig {
    private final JwtUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        // h2-console 사용 및 resources 접근 허용 설정
//        return (web) -> web.ignoring()
//                .requestMatchers(PathRequest.toH2Console())
////                // 정적 자원에 대해서는 Security 설정을 적용하지 않음
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
//    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf().disable();
        http.httpBasic().disable(); // REST API를 사용하기 때문에 비활성
        http.formLogin().disable(); //

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        /*
        SessionCreationPolicy.ALWAYS        - 스프링시큐리티가 항상 세션을 생성
        SessionCreationPolicy.IF_REQUIRED - 스프링시큐리티가 필요시 생성(기본)
        SessionCreationPolicy.NEVER           - 스프링시큐리티가 생성하지않지만, 기존에 존재하면 사용
        SessionCreationPolicy.STATELESS     - 스프링시큐리티가 생성하지도않고 기존것을 사용하지도 않음
                                                                  ->JWT 같은토큰방식을 쓸때 사용하는 설정
        */

        http.authorizeRequests()
                .antMatchers("/api/user/signup").permitAll()
                .antMatchers("/api/user/login").permitAll()
                .antMatchers("/api/user/checkup").permitAll()
                .antMatchers("/api/boards").hasAnyRole("Gryffindor", "Hufflepuff", "Ravenclaw", "Slytherin")
                .antMatchers("/api/board/**").hasAnyRole("Gryffindor", "Hufflepuff", "Ravenclaw", "Slytherin")
                .antMatchers("/api/comment/**").hasAnyRole("Gryffindor", "Hufflepuff", "Ravenclaw", "Slytherin")
                .anyRequest().authenticated();

        http.cors();    // corsConfigurationSource를 적용하기 위한 설정

        // Custom 로그인 페이지 사용
//        http.formLogin().loginPage("/api/user/login-page").permitAll();

        // JwtAuthFilter 등록하기
//        http.addFilterBefore(new CustomSecurityFilter( passwordEncoder()), UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);


        // 접근 제한 페이지 이동 설정
//        http.exceptionHandling().accessDeniedPage("/api/user/forbidden");

        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        config.addAllowedOrigin("http://localhost:3000");

        config.addExposedHeader(JwtUtil.AUTHORIZATION_HEADER);

        config.addAllowedMethod("*");

        config.addAllowedHeader("Content-Type");
        config.addAllowedHeader(JwtUtil.AUTHORIZATION_HEADER);

        config.setAllowCredentials(true);

        config.validateAllowCredentials();

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
