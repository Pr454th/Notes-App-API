package com.example.NotesAppAPI.Events;

import com.example.NotesAppAPI.Entities.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class PasswordResetTokenEvent extends ApplicationEvent {
    User user;
    String applicationURL;
    public PasswordResetTokenEvent(User user,String applicationURL) {
        super(user);
        this.user=user;
        this.applicationURL=applicationURL;
    }
}
