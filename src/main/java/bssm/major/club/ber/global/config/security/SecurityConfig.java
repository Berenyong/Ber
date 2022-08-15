package bssm.major.club.ber.global.config.security;

import bssm.major.club.ber.global.auth.CustomUserDetailService;
import bssm.major.club.ber.global.jwt.JwtTokenProvider;
import bssm.major.club.ber.global.jwt.JwtValidateService;
import bssm.major.club.ber.global.jwt.filter.JwtAuthenticationFilter;
import bssm.major.club.ber.global.jwt.filter.JwtExceptionFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailService customUserDetailService;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtValidateService jwtValidateService;

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
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/user/**").permitAll()
                // 비밀번호를 모르는 상태이기 때문에 인증이 필요없어야 함
                .antMatchers("/auth/logout").authenticated()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/email/delete").authenticated()
                .antMatchers("/email/**").permitAll()
                .antMatchers("/manager/find/**").permitAll()
                .antMatchers("/manager/comment/all/**").permitAll()
                .antMatchers("/manager/**")
                .access("hasRole('MANAGER') or hasRole('ADMIN')")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, customUserDetailService, jwtValidateService),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtExceptionFilter(), JwtAuthenticationFilter.class);

    }
}
