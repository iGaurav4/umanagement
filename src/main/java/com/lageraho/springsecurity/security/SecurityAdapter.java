package com.lageraho.springsecurity.security;


import com.lageraho.springsecurity.exception.CustomAccessDeniedHandler;
import com.lageraho.springsecurity.filter.JwtRequestFilter;
import com.lageraho.springsecurity.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityAdapter extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }

    //Authorisation
    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http.cors().and().csrf().disable()
//                        .authorizeRequests().antMatchers("/api/authenticate").permitAll()
//                        .anyRequest().authenticated();

        http.cors().and().csrf().disable()
                .authorizeRequests().antMatchers("/api/authenticate").permitAll()
                .antMatchers("/**/*.{js,html,css}").permitAll().anyRequest().authenticated()
//                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                .and().antMatcher("/api/**")
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }
    //Authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//                auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider());

    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
//        return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }

}
