package com.example.NotesAppAPI.DTO;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    Long Id;
    String firstName;
    String lastName;
    String email;
    String password;
    String role;
}
