package com.example.NotesAppAPI.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NoteRequest {
    @NotBlank(message = "Title required!")
    private String noteTitle;
    @NotBlank(message = "Content required!")
    private String noteContent;
    private Long userId;
}
