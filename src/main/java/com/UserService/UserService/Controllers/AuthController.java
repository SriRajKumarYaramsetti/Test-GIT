package com.UserService.UserService.Controllers;


import com.UserService.UserService.Dtos.*;
import com.UserService.UserService.Models.SessionStatus;
import com.UserService.UserService.Services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;


    public AuthController(AuthService authService){
        this.authService=authService;
    }

   // @PostMapping("/logout")
   // public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto request) {
  //      return authService.logout(request.getToken(), request.getUserId());
  //  }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpRequestDto request) {
        UserDto userDto = authService.signUp(request.getEmail(), request.getPassword());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

//    @PostMapping("/login")
//    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto request) {
//       return authService.login(request.getEmail(), request.getPassword());
//
//   }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto request) {
        return authService.logout(request.getToken(), request.getUserId());
    }

//    @GetMapping("/signup")
//    public String signUp() {
////        UserDto userDto = authService.signUp(request.getEmail(), request.getPassword());
//        return "hello";
//    }

  //  @PostMapping("/validate")
  //  public ResponseEntity<SessionStatus> validateToken(ValidateTokenRequestDto request) {
    //    SessionStatus sessionStatus = authService.validate(request.getToken(), request.getUserId());

      //  return new ResponseEntity<>(sessionStatus, HttpStatus.OK);
  //  }

    @PostMapping("/validate")
    public ResponseEntity<SessionStatus> validateToken(ValidateTokenRequestDto request) {
        SessionStatus sessionStatus = authService.validate(request.getToken(), request.getUserId());

        return new ResponseEntity<>(sessionStatus, HttpStatus.OK);
    }


}
