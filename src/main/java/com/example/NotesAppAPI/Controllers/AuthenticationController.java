package com.example.NotesAppAPI.Controllers;

import com.example.NotesAppAPI.DTO.JWTAuthenticationResponse;
import com.example.NotesAppAPI.DTO.PasswordResetRequest;
import com.example.NotesAppAPI.DTO.SignInRequest;
import com.example.NotesAppAPI.DTO.SignUpRequest;
import com.example.NotesAppAPI.Entities.PasswordResetToken;
import com.example.NotesAppAPI.Entities.User;
import com.example.NotesAppAPI.Events.PasswordResetTokenEvent;
import com.example.NotesAppAPI.Exceptions.InvalidCredentialException;
import com.example.NotesAppAPI.Services.AuthenticationService;
import com.example.NotesAppAPI.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @PostMapping("/signup")
    public ResponseEntity<JWTAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
        return new ResponseEntity<>(authenticationService.signup(request),HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<JWTAuthenticationResponse> signin(@RequestBody SignInRequest request) throws InvalidCredentialException {
        return new ResponseEntity<>(authenticationService.signin(request),HttpStatus.OK);
    }

    @GetMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest resetRequest, HttpServletRequest request){
        User user=userService.findUserByEmail(resetRequest.getEmail());
        if(user!=null){
            applicationEventPublisher.publishEvent(
                    new PasswordResetTokenEvent(user,applicationURL(request))
            );
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/savePassword")
    public ResponseEntity<?> savePassword(@RequestParam(name = "token") String token, @RequestBody PasswordResetRequest request){
        boolean isValidToken=userService.isValidResetToken(token);
        if(isValidToken){
            userService.changeUserPassword(token,request.getNewPassword());
            return new ResponseEntity<>("Password Reset Successful!",HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody PasswordResetRequest request) throws InvalidCredentialException {
        User user=userService.findUserByEmail(request.getEmail());
        if(user!=null){
            if(userService.isValidOldPassword(user,request.getOldPassword())){
                userService.changePasswordByUser(user,request.getNewPassword());
                return new ResponseEntity<>("Password Changed Successfully!",HttpStatus.OK);
            }
            else{
                throw new InvalidCredentialException("Old Password does match!");
            }
        }
        throw new InvalidCredentialException("Invalid user!");
    }

    String applicationURL(HttpServletRequest request){
        return "http://"+request.getServerName()+
                ":"+request.getServerPort()+
                "/api/v1"+request.getContextPath();
    }
}
