package com.UserService.UserService.Services;


import com.UserService.UserService.Dtos.UserDto;
import com.UserService.UserService.Models.Role;
import com.UserService.UserService.Models.Session;
import com.UserService.UserService.Models.SessionStatus;
import com.UserService.UserService.Models.User;
import com.UserService.UserService.Repositories.SessionRepository;
import com.UserService.UserService.Repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.*;

@Service
public class AuthService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private SessionRepository sessionRepository;


    public AuthService(UserRepository userRepository,BCryptPasswordEncoder bCryptPasswordEncoder,
                       SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.sessionRepository = sessionRepository;


    }

    public UserDto signUp(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));
        User savedUser = userRepository.save(user);
        return UserDto.from(savedUser);
    }


    public ResponseEntity<UserDto> login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
//            return this.signUp(email, password);
            return null;
        }

        User user = userOptional.get();

        if (!bCryptPasswordEncoder.matches(password, user.getHashedPassword())) {
            throw new RuntimeException("Wrong username password");
////            return null;
        }

        MacAlgorithm alg = Jwts.SIG.HS256; //or HS384 or HS256
        SecretKey key = alg.key().build();
       // String token = RandomStringUtils.randomAlphanumeric(30);
          Map<String, Object> jsonForJwt = new HashMap<>();
          jsonForJwt.put("email", user.getEmail());
          jsonForJwt.put("roles", user.getRoles());
          jsonForJwt.put("createdAt", new Date());
          jsonForJwt.put("expiryAt", new Date(LocalDate.now().plusDays(3).toEpochDay()));


         String token = Jwts.builder()
        .claims(jsonForJwt)
         .signWith(key, alg)
          .compact();

        Session session = new Session();
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setToken(token);
        session.setUser(user);
        sessionRepository.save(session);

        UserDto userDto = UserDto.from(user);

       // MacAlgorithm alg = Jwts.SIG.HS256; //or HS384 or HS256
        //SecretKey key = alg.key().build();

//        "hello".getBytes()

//        String message = "{\n" +
//                "   \"email\": \"naman@scaler.com\",\n" +
//                "   \"roles\": [\n" +
//                "      \"mentor\",\n" +
//                "      \"ta\"\n" +
//                "   ],\n" +
//                "   \"expirationDate\": \"23rdOctober2023\"\n" +
//                "}";
//        // user_id
//        // user_email
//        // roles
//        byte[] content = message.getBytes(StandardCharsets.UTF_8);

// Create the compact JWS:


//
//compact// Parse the compact JWS:
//        content = Jwts.parser().verifyWith(key).build().parseSignedContent(jws).getPayload();


//        Map<String, String> headers = new HashMap<>();
//        headers.put(HttpHeaders.SET_COOKIE, token);

        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, "auth-token:" + token);


        ResponseEntity<UserDto> response = new ResponseEntity<>(userDto, headers, HttpStatus.OK);
//        response.getHeaders().add(HttpHeaders.SET_COOKIE, token);

        return response;

    }

    public ResponseEntity<Void> logout(String token, Long userId) {
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);

        if (sessionOptional.isEmpty()) {
            return null;
        }

        Session session = sessionOptional.get();

        session.setSessionStatus(SessionStatus.ENDED);

        sessionRepository.save(session);

        return ResponseEntity.ok().build();
    }


    public SessionStatus validate(String token, Long userId) {
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);

        if (sessionOptional.isEmpty()) {
            return SessionStatus.ENDED;
//            return null;
        }

        Session session = sessionOptional.get();

        if (!session.getSessionStatus().equals(SessionStatus.ACTIVE)) {
            return SessionStatus.ENDED;
        }


        Jws<Claims> claimsJws = Jwts.parser()
                .build()
                .parseSignedClaims(token);

        String email = (String) claimsJws.getPayload().get("email");
        List<Role> roles = (List<Role>) claimsJws.getPayload().get("roles");
        Date createdAt = (Date) claimsJws.getPayload().get("createdAt");

        if (createdAt.before(new Date())) {
            return SessionStatus.ENDED;
        }


//        if (!session.)

        return SessionStatus.ACTIVE;
    }
}
