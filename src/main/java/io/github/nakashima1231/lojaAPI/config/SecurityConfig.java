package io.github.nakashima1231.lojaAPI.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()

                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/produtos/**").hasAnyRole("ADMIN", "VENDEDOR")
                        .requestMatchers("/produtos/**").hasRole("ADMIN")

                        // ... complete com clientes, vendedores e vendas
                        .requestMatchers(HttpMethod.DELETE, "/clientes/**").hasRole("ADMIN")
                        .requestMatchers("/clientes/**").hasAnyRole("ADMIN", "VENDEDOR")

                        .requestMatchers(HttpMethod.GET, "/vendedores/**").hasAnyRole("ADMIN", "VENDEDOR")
                        .requestMatchers("/vendedores/**").hasRole("ADMIN")

                        .requestMatchers("/vendas/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}