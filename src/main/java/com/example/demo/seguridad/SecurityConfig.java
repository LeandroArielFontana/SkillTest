package com.example.demo.seguridad;

import com.example.demo.servicios.UsuarioServicio;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder encoder;
    private final UsuarioServicio usuarioServicio;

    public final void configureGlobal(AuthenticationManagerBuilder authentication) throws Exception{
        authentication.userDetailsService(usuarioServicio).passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                .antMatchers("/login", "/usuario/crear", "/usuario/guardar", "/usuario/recuperarPass","/usuario/confirmarUsuario","/css/*", "/img/*", "/assets/*").permitAll()
                .antMatchers("/**").authenticated() //poner permitAll() cuando creemos el 1er usuario, desp poner authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                        .loginProcessingUrl("/logincheck")
                        .usernameParameter("nombreUsuario")
                        .passwordParameter("contrasenia")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                .and()
                    .logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                        .deleteCookies("JSESSIONID")
                .and()
                    .csrf()
                    .disable();
    }
}