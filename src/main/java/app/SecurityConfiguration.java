package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
@Autowired
public UserRepositoryAuthenticationProvider authenticationProvider;
	
 @Override
 protected void configure(HttpSecurity http) throws Exception {
	 
 // Public pages
 http.authorizeRequests().antMatchers("/").permitAll()
 .antMatchers("/login").permitAll()
 .antMatchers("/loginerror").permitAll()
 .antMatchers("/logout").permitAll()
 .antMatchers("/usuarios/alta/").permitAll()
 .antMatchers("/usuarios/nuevo").permitAll()
 .antMatchers("/**").hasRole("USER").anyRequest().permitAll()
 .antMatchers("/**").hasAuthority("USER").anyRequest().permitAll()
 .anyRequest().denyAll();
 //.antMatchers("/**").hasRole("USER").anyRequest().permitAll()
 //.antMatchers("/**").hasAuthority("USER").anyRequest().permitAll();
 
 //.antMatchers("/**").hasRole("USER").anyRequest().authenticated();
 //http.authorizeRequests().anyRequest().denyAll();
 //http.authorizeRequests().anyRequest().denyAll();


 
 // Private pages (all other pages)
 
 //http.authorizeRequests().anyRequest().hasAuthority("ANONYMOUS").anyRequest().denyAll();
 
 
 // Login form
 http.formLogin().loginPage("/login");
 http.formLogin().usernameParameter("nombre");
 http.formLogin().passwordParameter("passwordHash");
 http.formLogin().defaultSuccessUrl("/");
 http.formLogin().failureUrl("/loginerror");
 
 // Logout
 http.logout().logoutUrl("/logout");
 http.logout().logoutSuccessUrl("/");

 // Disable CSRF at the moment
 http.csrf().disable();
 }
 @Override
 protected void configure(AuthenticationManagerBuilder auth)
 throws Exception {
	 
 // Database authentication provider
 auth.authenticationProvider(authenticationProvider);
 }
 
}