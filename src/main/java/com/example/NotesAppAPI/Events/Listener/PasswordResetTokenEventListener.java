package com.example.NotesAppAPI.Events.Listener;

import com.example.NotesAppAPI.Entities.User;
import com.example.NotesAppAPI.Events.PasswordResetTokenEvent;
import com.example.NotesAppAPI.Services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class PasswordResetTokenEventListener implements ApplicationListener<PasswordResetTokenEvent> {
    @Autowired
    UserService userService;
    @Override
    public void onApplicationEvent(PasswordResetTokenEvent event) {
        User user=event.getUser();
        if(user!=null){
            String token= UUID.randomUUID().toString();
            userService.savePasswordResetToken(user,token);

            String url=event.getApplicationURL()+"/savePassword?token="+token;
            log.info("Click this link to reset password for your account : {}",url);
        }
    }
}
