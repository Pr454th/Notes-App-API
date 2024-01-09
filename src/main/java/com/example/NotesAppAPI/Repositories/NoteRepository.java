package com.example.NotesAppAPI.Repositories;

import com.example.NotesAppAPI.Entities.Note;
import com.example.NotesAppAPI.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note,Long> {
    List<Note> findAllByUser(User user);
}
