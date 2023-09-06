package com.mindhub.homebanking.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class WebAuthorization {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/rest/**").hasAuthority("ADMIN")
                //client repository
                .antMatchers(HttpMethod.GET,"/api/clients" ).hasAuthority("ADMIN")
                //.antMatchers(HttpMethod.GET,"/api/clients/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET,"/api/current").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.GET,"/api/clients/current").hasAuthority("CLIENT")
                //.antMatchers(HttpMethod.GET,"/api/clients/current").hasAuthority("CLIENT","ADMIN")
                .antMatchers(HttpMethod.POST,"/api/clients").permitAll()
                //account repository
                .antMatchers(HttpMethod.GET,"/api/accounts").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET,"/api/accounts/**").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST,"/api/clients/current/accounts").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.GET,"/api/clients/current/accounts").hasAuthority("CLIENT")
                //cards repository
                .antMatchers(HttpMethod.GET,"/api/cards").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST ,"/api/clients/current/cards").hasAuthority("CLIENT")
                //transaccionrepository
                .antMatchers(HttpMethod.POST ,"/api/transactions").hasAuthority("CLIENT")
                //frond
                .antMatchers(HttpMethod.GET,"/web/**").permitAll()
                .anyRequest().denyAll();
                //.anyRequest().permitAll();

        http.formLogin()

                .usernameParameter("email")

                .passwordParameter("password")

                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout");
        // si el cierre de sesión es exitoso,envíe una respuesta de éxito
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

        http.csrf().disable();

        //deshabilitar frameOptions para poder acceder a h2-console
        http.headers().frameOptions().disable();

        // Si el inicio de sesión es exitoso, borre las handler que solicitan autenticación.
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // si el inicio de sesión falla, envíe una respuesta de error de autenticación
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // Si el usuario no está autenticado, envíe una respuesta de error de autenticación.
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));


        return http.build();
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }

    }
}
