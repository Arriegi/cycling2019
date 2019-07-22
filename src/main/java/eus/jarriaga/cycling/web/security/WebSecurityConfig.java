package eus.jarriaga.cycling.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@ComponentScan
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier("customUserDetailsService")
    UserDetailsService userDetailsService;

    @Autowired
    DataSource dataSource;

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        //http.headers().frameOptions().disable();
        // La siguiente línea es necesaria paraque el download del fichero excel sea posible (si no da un error de X-frame...denny)
        http.headers().frameOptions().sameOrigin();
        //http.authorizeRequests().antMatchers("**").permitAll();
        //http.authorizeRequests().anyRequest().permitAll()
        http.authorizeRequests().antMatchers("/login**","/css/**","/js/**","/img/**", "/swagger**","/csrf","/v2/*",
                        "/webjars/**","/swagger-resources/**","/webfonts/**","/imputador**").permitAll()
                .and()
                .authorizeRequests().antMatchers("/swagger-ui.html").permitAll()
                .and()
                .authorizeRequests().antMatchers("/userlist.html").permitAll()
                .and()
                .authorizeRequests().antMatchers("favicon.ico").permitAll()
                /*
                .and()
                .authorizeRequests().antMatchers("/workparts/*").permitAll()
                 */
                .and()
                .authorizeRequests().antMatchers("/workparts/*").hasAnyRole("ADMIN", "USER")
                .and()
                .authorizeRequests().antMatchers("/expenses/*").hasAnyRole("ADMIN", "USER")
                .and()
                .authorizeRequests().antMatchers("/consoleUser.html").hasAnyRole("ADMIN", "USER")
                .and()
                .authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
                .and()
                //.authorizeRequests().antMatchers("/api/v1/*/*").hasAnyRole("ADMIN", "USER")
                .authorizeRequests().antMatchers("/api/v1/**").hasAnyRole("ADMIN", "USER")
                .and()
                //.authorizeRequests().anyRequest().authenticated()
                .authorizeRequests().anyRequest().hasRole("ADMIN")
                .and()
                .httpBasic()
                .and()
                //.formLogin().loginPage("/login_userlist.html").loginProcessingUrl("/login").successHandler(myAuthenticationSuccessHandler()).failureUrl("/login.html?login_error=1").permitAll();
                //.formLogin().loginPage("/login_userlist.html").successHandler(myAuthenticationSuccessHandler()).failureUrl("/login.html?login_error=1").permitAll();    }
                .formLogin().loginPage("/login_userlist.html").loginProcessingUrl("/login").successHandler(myAuthenticationSuccessHandler()).failureUrl("/login_userlist.html?login_error=1").permitAll()
                /*
                .and()
                .rememberMe()
                .tokenValiditySeconds(1296000)
                .key("imputador20190506")
                 */
                .and()
                .logout().logoutSuccessUrl("/login_userlist.html").permitAll()
        ;    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MySimpleUrlAuthenticationSuccessHandler();
    }

    @Bean
    public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint(){
        return new CustomBasicAuthenticationEntryPoint();
    }

}
