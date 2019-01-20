package dev.mateusz.barber.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private DataSource securityDataSource;

	@Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.
        jdbcAuthentication()
        .usersByUsernameQuery(usersQuery)
        .authoritiesByUsernameQuery(rolesQuery)
        .dataSource(securityDataSource)
        .passwordEncoder(bCryptPasswordEncoder);
	}
	
	@Override
    protected void configure(HttpSecurity http) throws Exception{

		http.authorizeRequests()
			.antMatchers("/").hasRole("CUSTOMER")
			.antMatchers("/orders/**").hasRole("MODERATOR")
			.antMatchers("/crm/**").hasRole("ADMIN")
		.and()
		.formLogin()
			.loginPage("/showLoginPage")
			.loginProcessingUrl("/authenticateTheUser")
			.permitAll()
		.and()
			.logout().permitAll()
		.and()
			.exceptionHandling()
			.accessDeniedPage("/access-denied");
	
	}
}
