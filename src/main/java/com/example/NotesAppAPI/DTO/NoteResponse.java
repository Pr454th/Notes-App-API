package com.example.NotesAppAPI.DTO;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoteResponse {
    private Long noteId;
    private String noteTitle;
    private String noteContent;
    private Long userId;
}
