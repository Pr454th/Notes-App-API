package com.example.NotesAppAPI.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Note {
    @Id
    @SequenceGenerator(
            name="note_id_sequence",
            sequenceName = "note_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "note_id_sequence"
    )
    private Long id;
    private String noteTitle;
    private String noteContent;
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name="user_id",
            foreignKey = @ForeignKey(name = "FK_USER_NOTE")
    )
    private User user;
}
