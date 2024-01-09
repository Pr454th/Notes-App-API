package com.example.NotesAppAPI.Services;

import com.example.NotesAppAPI.DTO.SignUpRequest;
import com.example.NotesAppAPI.Entities.PasswordResetToken;
import com.example.NotesAppAPI.Entities.Role;
import com.example.NotesAppAPI.Entities.User;
import com.example.NotesAppAPI.Repositories.PasswordResetTokenRepository;
import com.example.NotesAppAPI.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Calendar;

@Service
public class UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository prtRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                return userRepository.findByEmail(email);
            }
        };
    }

    public User save(SignUpRequest signUpRequest){
        User user= new User();
        if(signUpRequest.getId()==null){
            user.setCreatedAt(LocalDateTime.now());
        }
        user.setUpdatedAt(LocalDateTime.now());
        user.setEmail(signUpRequest.getEmail());
        user.setRole(Role.valueOf(signUpRequest.getRole()));
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setPassword(signUpRequest.getPassword());
        userRepository.save(user);
        return user;
    }

    public User findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public void savePasswordResetToken(User user, String token) {
        PasswordResetToken passwordResetToken=new PasswordResetToken(user,token);
        prtRepository.save(passwordResetToken);
    }

    public boolean isValidResetToken(String token) {
        PasswordResetToken passwordResetToken=prtRepository.findByToken(token);
        if(passwordResetToken!=null){
            User user=passwordResetToken.getUser();
            Calendar calendar=Calendar.getInstance();
            if(calendar.getTime().getTime()-passwordResetToken.getExpirationTime().getTime()>=0) {
                prtRepository.delete(passwordResetToken);
                return false;
            }
        }
        return true;
    }

    public void changeUserPassword(String token, String newPassword) {
        User user=prtRepository.findByToken(token).getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public boolean isValidOldPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword,user.getPassword());
    }

    public void changePasswordByUser(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
