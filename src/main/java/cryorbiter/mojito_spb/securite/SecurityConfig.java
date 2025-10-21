package cryorbiter.mojito_spb.securite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import jakarta.annotation.PostConstruct;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/*.css", "/*.svg", "/*.jpg", "/*.jpeg", "/*.png", "/*.gif",
                                "/*.js","/images/**", "/inc/**").permitAll()
                        .requestMatchers("/", "/home").authenticated()
                        .anyRequest().authenticated())
                .formLogin(log -> log
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider getAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);

        return  authenticationProvider;
    }

    @PostConstruct
    public void convertDatabaseUrl() {
        String databaseUrl = System.getenv("Postgres.DATABASE_URL");
        if (databaseUrl != null && databaseUrl.startsWith("postgresql://")) {
            String springUrl = "jdbc:" + databaseUrl.replace("postgresql://", "postgresql://").replace("?", "&");
            System.setProperty("SPRING_DATASOURCE_URL", springUrl);
        }
    }
}
