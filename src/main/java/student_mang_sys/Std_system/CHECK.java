package student_mang_sys.Std_system;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class CHECK {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // disable CSRF
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()); // sab endpoints open
        return http.build();
    }
}
