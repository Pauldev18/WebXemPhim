package com.WebXemPhim.WebXemPhim.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//@Configuration
//@EnableWebSecurity
//public class OTPConfig extends WebSecurityConfigurerAdapter {
//
////    @Override
////    protected void configure(HttpSecurity http) throws Exception {
////        http
////                .csrf().disable()
////                .authorizeRequests()
////                .antMatchers("/getNgayPhim/**", "/*").permitAll()
////                .antMatchers("/**/**", "/*").permitAll()// Cho phép mọi người truy cập /generateOtp mà không cần xác thực
////                .anyRequest().authenticated()
////                .and()
////                .formLogin()
////                .and()
////                .httpBasic();
////    }
//}
