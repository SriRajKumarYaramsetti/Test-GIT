package com.UserService.UserService.Controllers;

import com.UserService.UserService.Dtos.LoginRequestDto;
import com.UserService.UserService.Dtos.LogoutRequestDto;
import com.UserService.UserService.Dtos.SignUpRequestDto;
import com.UserService.UserService.Dtos.UserDto;
import com.UserService.UserService.Models.Token;
import com.UserService.UserService.Services.UserService;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping("/login")
//    public Token login(@RequestBody LoginRequestDto request){
//
//        //Check if email and password are in db
//        //If Yes return User
//        //Else throw some error
//
//        return null;
//    }

    @PostMapping("/signup")
    public User signUp(@RequestBody SignUpRequestDto request){
        String email=request.getEmail();
        String password=request.getPassword();




        return null;
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto request ){
        // delete token if exists -> 200
        // if doesn't exist give a 404
        String token= request.getToken();
        userService.logout(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
