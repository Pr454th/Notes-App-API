package com.example.NotesAppAPI.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name="password_reset_token_tbl"
)
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String token;
    @OneToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name="user_id",
            foreignKey = @ForeignKey(name = "FK_USER_PASSWORD_RESET_TOKEN")
    )
    User user;
    Date expirationTime;

    public PasswordResetToken(User user,String token){
        super();
        this.user=user;
        this.token=token;
        this.expirationTime=calculateExpirationDate();
    }

    private Date calculateExpirationDate() {
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE,10);
        return new Date(calendar.getTime().getTime());
    }

}
