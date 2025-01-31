package com.UserService.UserService.Services;

import com.UserService.UserService.Dtos.UserDto;
import com.UserService.UserService.Models.Token;
import com.UserService.UserService.Models.User;
import com.UserService.UserService.Repositories.TokenRepository;
import com.UserService.UserService.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private TokenRepository tokenRepository;

    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public Token login( String email,String Password){
       Optional<User> userOptional =userRepository.findByEmail(email);


       if (userOptional.isEmpty()){
           return null;
       }
        return null;
    }

    public User signUp(String fullName ,String email,
                          String password){

        User user=new User();
        user.setEmail(email);

        user.setHashedPassword(password);

        User savedUser=userRepository.save(user);
        UserDto responseDto=new UserDto();

        return savedUser;
    }


    public void logout(String token){
        Optional<Token> token1=tokenRepository.findByValueAndDeletedEquals(token,false);
        Token Receivedtoken=token1.get();
        Receivedtoken.setDeleted(true);
        tokenRepository.save(Receivedtoken);

    }
}
