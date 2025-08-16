package student_mang_sys.Std_system;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/employees/fit", "/employees/login").permitAll() // ✅ public endpoints
                .anyRequest().authenticated()
            )
            .httpBasic(); // optional

        return http.build();
    }
}
