package CS4050.Software.BookIt.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	
	
	

	@Autowired
	AuthenticationSuccessHandler successHandler;
	 
	 @Autowired
	 private DataSource dataSource;
	 
	 private final String USERS_QUERY = "select email, password, status from user_info where email=?";
	 private final String ROLES_QUERY = "select email, role from user_info where email=?";

	 @Override
	 protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	  auth.jdbcAuthentication()
	   .usersByUsernameQuery(USERS_QUERY)
	   .authoritiesByUsernameQuery(ROLES_QUERY)
	   .dataSource(dataSource)
	   .passwordEncoder(new BCryptPasswordEncoder());
	  
	 }	
	
	
	
  @Override
  protected void configure(HttpSecurity http) throws Exception {
	  
    http.headers().frameOptions().disable();
    http.authorizeRequests()
    .antMatchers("/CSS/**").permitAll()
    .antMatchers("/plugins/**").permitAll()
    .antMatchers("/welcome").permitAll()
    .antMatchers("/login").permitAll()
    .antMatchers("/forgotpassword").permitAll()
    .antMatchers("/signup").permitAll()
    .antMatchers("/signupcomplete").permitAll()
    .antMatchers("/accountactivation").permitAll()
    .antMatchers("/home/adminhome/**").hasAuthority("Admin")
    .antMatchers("/home/customerlogin/**").hasAuthority("customer")
    .antMatchers("/default").hasAnyAuthority("Admin", "customer").anyRequest()
    .authenticated()
    .and().csrf().disable()
    .formLogin().loginPage("/login")
    .failureUrl("/login?error=true")
    .defaultSuccessUrl("/default")
    .usernameParameter("email")
    .passwordParameter("password")

    .and().logout()
    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
    .logoutSuccessUrl("/welcome")
    .and().rememberMe()
    .tokenRepository(persistentTokenRepository())
    .tokenValiditySeconds(60*60)
    .and().exceptionHandling().accessDeniedPage("/access_denied");
  }
  @Bean
  public PersistentTokenRepository persistentTokenRepository() {
   JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
   db.setDataSource(dataSource);
   
   return db;
  }
  
}
