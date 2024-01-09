package com.example.NotesAppAPI.Controllers;

import com.example.NotesAppAPI.DTO.NoteRequest;
import com.example.NotesAppAPI.DTO.NoteResponse;
import com.example.NotesAppAPI.Exceptions.InvalidUserException;
import com.example.NotesAppAPI.Exceptions.NoteNotFoundException;
import com.example.NotesAppAPI.Exceptions.UserNotFoundException;
import com.example.NotesAppAPI.Services.NoteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class NoteController {
    @Autowired
    private NoteService noteService;

    @PostMapping("/note")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<?> saveFile(@RequestBody @Valid NoteRequest note, HttpServletRequest request) throws UserNotFoundException, UserNotFoundException {
        noteService.addNote(note,request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/note")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<List<NoteResponse>> getAllNotesByUser(HttpServletRequest request){
        List<NoteResponse> notes=noteService.getAllNotesByUser(request);
        return new ResponseEntity<>(notes,HttpStatus.OK);
    }

    @PutMapping("/note/{id}")
    ResponseEntity<NoteResponse> updateNote(@PathVariable(value="id") Long id,@RequestBody NoteRequest note,HttpServletRequest request) throws NoteNotFoundException, InvalidUserException {
        noteService.updateNote(id,note,request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/note/{id}")
    ResponseEntity<?> deleteNote(@PathVariable(value="id") Long id,HttpServletRequest request) throws NoteNotFoundException, InvalidUserException {
        noteService.deleteNote(id,request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
