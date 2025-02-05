package com.UserService.UserService.Security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurity {


//
//    //@Bean
//   // public SecurityFilterChain filteringCriteria(HttpSecurity http) throws Exception {
//
//        //http.cors().disable();
//        //http.csrf().disable();
//        //http.authorizeHttpRequests(authorize->authorize.anyRequest().permitAll());
//       // return http.build();
//
//                //Security filter chain object handles what all api endpoints should be authenticated
//
//                //VS what all should not be authenticated.
//    //}
//
//
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
