package org.idk.springsecurity.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class DemoSecurityConfig {
    /*
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails john = User.builder()
                    .username("john")
                    .password("{noop}123")
                    .roles("EMPLOYEE")
                    .build();
        UserDetails mary = User.builder()
                .username("mary")
                .password("{noop}123")
                .roles("EMPLOYEE","LEADER")
                .build();
        UserDetails susan = User.builder()
                .username("susan")
                .password("{noop}123")
                .roles("EMPLOYEE","LEADER","ADMIN")
                .build();
        return new InMemoryUserDetailsManager(john, mary, susan);
    }
    */
    // add support for JDBC
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
//        return new JdbcUserDetailsManager(dataSource);
        JdbcUserDetailsManager theUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        theUserDetailsManager
                .setUsersByUsernameQuery("select user_id, pw, active from members where user_id=?");
        theUserDetailsManager
                .setAuthoritiesByUsernameQuery(
                        "select user_id, role from roles where user_id=?"
                );
        return theUserDetailsManager;
    }
    @Bean
    public SecurityFilterChain filterChange(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers("/").hasRole("EMPLOYEE")
                        .requestMatchers("/leader").hasRole("MANAGER")
                        .requestMatchers("/system").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(form ->
                        form
                                .loginPage("/showMyLoginPage")
                                .loginProcessingUrl("/authenticateTheUser")
                                .permitAll()
                )
                .logout(logout -> logout.permitAll())
                .exceptionHandling(exception -> exception.accessDeniedPage("/access-denied"));

        return http.build();
    }
}
