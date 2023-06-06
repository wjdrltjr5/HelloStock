package deu.hellostock.config;

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;
import deu.hellostock.entity.Role;
import deu.hellostock.service.CustomOauth2MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final CustomOauth2MemberService customOauth2MemberService;
    private final AuthenticationFailureHandler customFailureHandler;
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/","/stock").permitAll()
                .antMatchers("/board/**","/mystock","/mypage").hasRole("USER")
                .and()
                .formLogin()
                .loginPage("/signin")
                .loginProcessingUrl("/signin")
                .failureHandler(customFailureHandler)
                .defaultSuccessUrl("/")
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOauth2MemberService);
        return http.build();
    }
    @Bean
    public FilterRegistrationBean<XssEscapeServletFilter> filterRegistrationBean() {
        FilterRegistrationBean<XssEscapeServletFilter> filterRegistration = new FilterRegistrationBean<>();
        filterRegistration.setFilter(new XssEscapeServletFilter());
        filterRegistration.setOrder(1);
        filterRegistration.addUrlPatterns("/*");
        return filterRegistration;
    }
}
