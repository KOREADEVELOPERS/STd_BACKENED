package student_mang_sys.Std_system;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class CHECK {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // ✅ Correct lambda-style syntax
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()   // ✅ Allow all requests
                )
                .httpBasic(Customizer.withDefaults()); // Optional: basic auth setup
        return http.build();
    }
}
