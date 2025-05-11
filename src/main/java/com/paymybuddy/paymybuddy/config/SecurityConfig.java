package com.paymybuddy.paymybuddy.config;

import com.paymybuddy.paymybuddy.services.UserDetailsServiceImpl;
import com.paymybuddy.paymybuddy.services.interfaces.IUser;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    private final UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http
                .getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(daoAuthenticationProvider())
                .build();
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/", "/login", "/register").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/j_spring_security_check")
                        .defaultSuccessUrl("/profile", true)
                        .failureUrl("/login?error=true")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .maximumSessions(1))
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/errorpage"));

        return http.build();
    }

    /**
     * @Bean
     *     InternalResourceViewResolver viewResolver() {
     *         InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
     *         viewResolver.setPrefix("/WEB-INF/views/");
     *         viewResolver.setSuffix(".jsp");
     *         return viewResolver;
     *     }
     */

    /**
     * @Bean
     *     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
     *         http
     *                 .authorizeHttpRequests(authz -> authz
     *                         .requestMatchers("/", "/home", "/productList", "/shoppingCart", "/admin/login", "/css/**", "/js/**", "/images/**", "/errorpage", "/error")
     *                         .permitAll()
     *                         .requestMatchers("/admin/orderList", "/admin/order", "/admin/accountInfo", "/admin/logout", "/admin/index")
     *                         .hasAnyRole("EMPLOYEE", "MANAGER")
     *                         .requestMatchers("/admin/product", "/admin/**")
     *                         .hasRole("MANAGER")
     *                         .anyRequest()
     *                         .authenticated()
     *                 )
     *                 .userDetailsService(userDetailsServiceImpl)
     *                 .formLogin(form -> form
     *                         .loginPage("/admin/login")
     *                         .loginProcessingUrl("/j_spring_security_check")
     *                         .defaultSuccessUrl("/admin/accountInfo", true)
     *                         .failureUrl("/admin/login?error=true")
     *                         .usernameParameter("email")
     *                         .passwordParameter("password")
     *                         .permitAll()
     *                 )
     *                 .logout(logout -> logout
     *                         .logoutUrl("/admin/logout")
     *                         .logoutSuccessUrl("/")
     *                         .invalidateHttpSession(true)
     *                         .clearAuthentication(true)
     *                         .permitAll()
     *                 )
     *                 .sessionManagement(session -> session
     *                         .maximumSessions(1)
     *                 )
     *                 .exceptionHandling(exception -> exception
     *                         .accessDeniedPage("/errorpage")
     *                 );
     *
     *         return http.build();
     *     }
     */


}
