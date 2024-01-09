package com.example.NotesAppAPI.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NoteRequest {
    private String noteTitle;
    private String noteContent;
    private Long userId;
}
