package lk.ecommerce.zeetradexbackend.config;

//we not need spring security default password
//will create our own password will give our own password and username using that credential will allow login to user

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Configuration
public class AppConfig {

    private final CorsConfigurationSource corsConfigurationSource;

    public AppConfig(CorsConfigurationSource corsConfigurationSource) {
        this.corsConfigurationSource = corsConfigurationSource;
    }
  @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(management->management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(Authorize->Authorize.requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll())
                .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                .csrf(csrf->csrf.disable())
                .cors(cors->cors.configurationSource(corsConfigurationSource()));

        return http.build();
    }
// cors configuration
    private CorsConfigurationSource corsConfigurationSource(){

      return new CorsConfigurationSource() {
          @Override
          public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
              CorsConfiguration cfg = new CorsConfiguration();
              cfg.setAllowedOrigins(
                      Arrays.asList(
                              "http://localhost:5174",
                              "http://localhost:8080"
                      )
              );

              cfg.setAllowedMethods(Collections.singletonList("*"));
              cfg.setAllowPrivateNetwork(true);
              cfg.setExposedHeaders(Arrays.asList(("Authorization")));
              cfg.setAllowedHeaders(Collections.singletonList("*"));
              cfg.setMaxAge(3600L);
              return cfg;
          }
      };
    }
}
