package com.example.library.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;


@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userService;
    private final JwtRequestFilter jwtRequestFilter;

/*    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**"

    };

    @Override
    public void configure(WebSecurity web){
        web.ignoring().antMatchers(AUTH_WHITELIST);
    }*/

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/webjars/**");
        web.ignoring().antMatchers("/swagger-resources/**");
        web.ignoring().antMatchers("/swagger-ui.html");
        web.ignoring().antMatchers("/v3/api-docs/**");
        web.ignoring().antMatchers("/swagger.json");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                    .disable()
                    .authorizeRequests()
                    .antMatchers("/users/register").anonymous()
                    .antMatchers("/users/login").anonymous()
                    .antMatchers("/users/new-admin").permitAll()
                    .antMatchers("/swagger-ui/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);



        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }



    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
