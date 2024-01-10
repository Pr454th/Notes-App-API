package com.example.NotesAppAPI.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    Long Id;
    @NotBlank(message = "FirstName required!")
    String firstName;
    @NotBlank(message = "LastName required!")
    String lastName;
    @NotBlank(message = "Valid Email required!")
    String email;
    @NotBlank(message = "Password required!")
    String password;
    String role;
}
