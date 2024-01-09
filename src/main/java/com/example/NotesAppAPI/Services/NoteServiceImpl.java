package com.example.NotesAppAPI.Services;

import com.example.NotesAppAPI.DTO.NoteRequest;
import com.example.NotesAppAPI.DTO.NoteResponse;
import com.example.NotesAppAPI.Entities.Note;
import com.example.NotesAppAPI.Entities.User;
import com.example.NotesAppAPI.Exceptions.InvalidUserException;
import com.example.NotesAppAPI.Exceptions.NoteNotFoundException;
import com.example.NotesAppAPI.Exceptions.UserNotFoundException;
import com.example.NotesAppAPI.Repositories.NoteRepository;
import com.example.NotesAppAPI.Repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService{
    @Autowired
    private JWTService jwtService;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    String getUserFromRequest(HttpServletRequest request){
        String authHeader=request.getHeader("Authorization");
        String jwt=authHeader.substring(7);
        String userEmail=jwtService.extractUserName(jwt);
        return userEmail;
    }
    @Override
    public void addNote(NoteRequest noteRequest, HttpServletRequest request) throws UserNotFoundException {
        String email=getUserFromRequest(request);
        User user=userRepository.findByEmail(email);
        Note note=Note.builder()
                .noteTitle(noteRequest.getNoteTitle())
                .noteContent(noteRequest.getNoteContent())
                .user(user)
                .build();
        noteRepository.save(note);
    }

    @Override
    public NoteResponse getNote(Long id) throws NoteNotFoundException {
        Optional<Note> note=noteRepository.findById(id);
        if(note.isPresent()){
            NoteResponse noteResponse=NoteResponse
                    .builder()
                    .noteId(note.get().getId())
                    .noteTitle(note.get().getNoteTitle())
                    .noteContent(note.get().getNoteContent())
                    .build();
            return noteResponse;
        }
        throw new NoteNotFoundException("No note found with id "+id);
    }

    @Override
    public void updateNote(Long id, NoteRequest noteRequest, HttpServletRequest request) throws NoteNotFoundException, InvalidUserException {
        Optional<Note> note=noteRepository.findById(id);
        if(note.isPresent()){
            String email=getUserFromRequest(request);
            User user=userRepository.findByEmail(email);
            if(Objects.equals(user.getId(), note.get().getUser().getId())){
                Note note1=note.get();
                note1.setNoteTitle(noteRequest.getNoteTitle());
                note1.setNoteContent(noteRequest.getNoteContent());
                noteRepository.save(note1);
                return;
            }
            else{
                throw new InvalidUserException("User`s are not permitted to modify other users notes!");
            }
        }
        throw new NoteNotFoundException("No note found with id "+id);
    }

    @Override
    public void deleteNote(Long id,HttpServletRequest request) throws NoteNotFoundException, InvalidUserException {
        Optional<Note> note=noteRepository.findById(id);
        if(note.isPresent()){
            String email=getUserFromRequest(request);
            User user=userRepository.findByEmail(email);
            if(Objects.equals(user.getId(), note.get().getUser().getId())){
                noteRepository.deleteById(id);
                return;
            }
            else{
                throw new InvalidUserException("User`s are not permitted to modify other users notes!");
            }
        }
        throw new NoteNotFoundException("No note found with id "+id);
    }

    @Override
    public List<NoteResponse> getAllNotesByUser(HttpServletRequest request) {
        String email=getUserFromRequest(request);
        User user=userRepository.findByEmail(email);
        List<Note> notes=noteRepository.findAllByUser(user);
        List<NoteResponse> result=new ArrayList<>();
        for(Note note:notes){
            NoteResponse noteResponse=NoteResponse
                    .builder()
                    .noteId(note.getId())
                    .noteTitle(note.getNoteTitle())
                    .noteContent(note.getNoteContent())
                    .build();
            result.add(noteResponse);
        }
        return result;
    }
}
