package YukEcmWeb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private SecurityProvider provider;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
        .antMatchers("/login").permitAll()
        .antMatchers("/**").authenticated();

        http.formLogin()
        .loginPage("/login")
        .defaultSuccessUrl("/", true);
        
        http.logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/login")
        .invalidateHttpSession(true);
        
        http.csrf().disable();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(this.provider);
	}
	
	@Override
	public void configure(WebSecurity web) 
	{
		web.ignoring().antMatchers("/js/**","/css/**","/assets/**","/atom11/**","/services11/**","/browser/**","/test.html");
	}
}
