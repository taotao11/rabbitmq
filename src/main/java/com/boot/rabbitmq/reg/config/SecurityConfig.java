package com.boot.rabbitmq.reg.config;

import com.boot.rabbitmq.reg.entity.LocalUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * security
 * 配置类
 *
 */
@Configuration
@EnableWebSecurity //开启security 整合oauth2.0 不用设置为开启security
//@Order(1) //加载顺序 值越小，优先级越高！
//第一步继承 SecurityConfigurerAdapter
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//用户获取
    @Bean
    public UserDetailsService userDetailsService(){
        return new LocalUserDetailsService();
    }
//密码验证
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
//
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

//    protected void configure(HttpSecurity http)throws Exception{
//
//        http.csrf().disable();//禁用CSRF
//        //允许所有人可以访问
//        http.authorizeRequests()
//                .antMatchers("/druid/**","/swagger-ui.html",
//                        "/login","/ace/**","/css/**","/images/**","/js/**").permitAll();
//        http.authorizeRequests().anyRequest().authenticated();//其他的认证后才能访问
//        http.formLogin()
//            .loginPage("/login")//登录页
//            .loginProcessingUrl("/login")//登录处理地址
//            .failureForwardUrl("/loginError")//指定失败跳转地址
//            .successForwardUrl("/main") //登录成功跳转地址
//            .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))//退出地址
//            .logoutSuccessUrl("/login") // 退出后指向的地址
//            .clearAuthentication(true)
//            .invalidateHttpSession(true)
//            .permitAll()
//            .and().headers().frameOptions().disable()
//            .and().sessionManagement().maximumSessions(1).expiredUrl("/logout");
//    }

    /**
     * 过滤链
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .requestMatchers().anyRequest()
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**").permitAll();
//        // @formatter:on

    }
    /**
     * 用户密码验证绑定
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());

        auth.authenticationProvider(authenticationProvider());

        auth.userDetailsService(userDetailsService());

    }
}
