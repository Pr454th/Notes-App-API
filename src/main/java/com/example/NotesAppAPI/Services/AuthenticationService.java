package com.example.NotesAppAPI.Services;

import com.example.NotesAppAPI.DTO.JWTAuthenticationResponse;
import com.example.NotesAppAPI.DTO.SignInRequest;
import com.example.NotesAppAPI.DTO.SignUpRequest;
import com.example.NotesAppAPI.Exceptions.InvalidCredentialException;
import com.example.NotesAppAPI.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationResponse signup(SignUpRequest request) {
        SignUpRequest signUpRequest = SignUpRequest
                .builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        var user = userService.save(signUpRequest);
        var jwt = jwtService.generateToken(user);
        return JWTAuthenticationResponse.builder().token(jwt).build();
    }

    public JWTAuthenticationResponse signin(SignInRequest request) throws InvalidCredentialException {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        }
        catch(AuthenticationException e){
            throw new InvalidCredentialException("Invalid Username/Password");
        }
        var user = userRepository.findByEmail(request.getEmail());
        var jwt = jwtService.generateToken(user);
        return JWTAuthenticationResponse.builder().token(jwt).build();
    }
}
