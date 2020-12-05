package org.bugtracker.api.config;

import org.bugtracker.api.security.JwtAuthenticationEntryPoint;
import org.bugtracker.api.security.JwtAuthenticationFilter;
import org.bugtracker.api.services.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        UserDetailsService userDetailsService;

        @Autowired
        private JwtAuthenticationEntryPoint unauthorizedHandler;

        @Bean
        public JwtAuthenticationFilter jwtAuthenticationFilter() {
                return new JwtAuthenticationFilter();
        }

        @Override
        public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
                authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        }

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
                return super.authenticationManagerBean();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
                http.cors();
                http.csrf().disable();
                http.headers().frameOptions().disable();
                http.exceptionHandling().authenticationEntryPoint(unauthorizedHandler);
                http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                http.authorizeRequests()
                        .antMatchers("/", "/h2-console/**", "/h2-console", "/swagger-ui/**", "/swagger-resources/**", "/v2/api-docs").permitAll()
                        .antMatchers(HttpMethod.GET, "/developers/**", "/bugs/**").permitAll()
                        .antMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated();

                http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        }
}
