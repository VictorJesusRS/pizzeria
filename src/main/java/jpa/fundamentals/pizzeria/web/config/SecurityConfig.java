package jpa.fundamentals.pizzeria.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity(securedEnabled = true)

public class SecurityConfig {
    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(customizeRequests -> {
                   customizeRequests
                           // "/*" indicate the level con route. So, "/api/*" means all routes in "/api/", amd it doesn't work on "/api/product/available"
                           // "/**" indicate all levels inside
                           // .requestMatchers(HttpMethod.GET, "/api/pizzas/**").permitAll()
                           .requestMatchers("/api/auth/**").permitAll()
                           .requestMatchers("/api/customers/**").hasAnyRole("ADMIN", "CUSTOMER")
                           .requestMatchers(HttpMethod.GET, "/api/pizzas/**").hasAnyRole("ADMIN", "CUSTOMER")
                           .requestMatchers(HttpMethod.POST, "/api/pizzas/**").hasRole("ADMIN")
                           .requestMatchers(HttpMethod.GET, "/api/orders/random").hasAuthority("random_order")
                           .requestMatchers(HttpMethod.GET, "/api/orders/**").hasAnyRole("ADMIN")
                           .requestMatchers("/swagger/**", "/v3/**").permitAll()
                           .anyRequest()
                           .authenticated();
                })
                //Disable csrf for security reasons as it is not needed in the project
//                .csrf((csrf) -> csrf.disable())
//                .csrf(AbstractHttpConfigurer::disable)
//                .cors(Customizer.withDefaults())
                .addFilterBefore(jwtFilter, BasicAuthenticationFilter.class);
//                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

//    @Bean
//    public UserDetailsService memoryUsers() {
//        UserDetails admin = User.builder()
//                .username("admin2")
//                .password(passwordEncoder().encode("admin123"))
//                .roles("ADMIN")
//                .build();
//
//        UserDetails customer = User.builder()
//                .username("customer2")
//                .password(passwordEncoder().encode("customer123"))
//                .roles("CUSTOMER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, customer);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
