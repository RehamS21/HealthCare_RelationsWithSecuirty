package com.example.healthcare_relationswithsecuirty.Config;


import com.example.healthcare_relationswithsecuirty.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfigSecurity {
    private final MyUserDetailsService myUserDetailsService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider1=new
                DaoAuthenticationProvider();

        daoAuthenticationProvider1.setUserDetailsService(myUserDetailsService);
        daoAuthenticationProvider1.setPasswordEncoder(new
                BCryptPasswordEncoder());
        return daoAuthenticationProvider1;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception{
        http.csrf().disable()
                .sessionManagement()

                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider( daoAuthenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/patient/register").permitAll()
                .requestMatchers("/api/v1/doctor/register").permitAll()
                .requestMatchers("/api/v1/auth/register").permitAll()

                .requestMatchers("/api/v1/bill/**").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/room/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/auth/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();
    }




}
