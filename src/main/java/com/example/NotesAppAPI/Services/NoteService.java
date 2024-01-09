package com.example.NotesAppAPI.Services;

import com.example.NotesAppAPI.DTO.NoteRequest;
import com.example.NotesAppAPI.DTO.NoteResponse;
import com.example.NotesAppAPI.Exceptions.InvalidUserException;
import com.example.NotesAppAPI.Exceptions.NoteNotFoundException;
import com.example.NotesAppAPI.Exceptions.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface NoteService {
    void addNote(NoteRequest noteRequest, HttpServletRequest request) throws UserNotFoundException;

    NoteResponse getNote(Long id) throws NoteNotFoundException;

    void updateNote(Long id, NoteRequest noteRequest,HttpServletRequest request) throws NoteNotFoundException, InvalidUserException;

    void deleteNote(Long id,HttpServletRequest request) throws NoteNotFoundException, InvalidUserException;

    List<NoteResponse> getAllNotesByUser(HttpServletRequest request);
}
