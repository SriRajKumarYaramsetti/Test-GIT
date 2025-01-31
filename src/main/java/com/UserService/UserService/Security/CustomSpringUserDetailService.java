package com.UserService.UserService.Security;

import com.UserService.UserService.Models.User;
import com.UserService.UserService.Repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomSpringUserDetailService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomSpringUserDetailService(UserRepository userRepository){
        this.userRepository=userRepository;

    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Optional<User> userOptional= userRepository.findByEmail(username);

       if(userOptional.isEmpty()){
          throw new UsernameNotFoundException("User Not Found");
       }

       User user=userOptional.get();


        return new CustomUserDetails(user);
    }
}
