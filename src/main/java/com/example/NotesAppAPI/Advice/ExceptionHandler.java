package com.example.NotesAppAPI.Advice;

import com.example.NotesAppAPI.Exceptions.InvalidCredentialException;
import com.example.NotesAppAPI.Exceptions.InvalidUserException;
import com.example.NotesAppAPI.Exceptions.NoteNotFoundException;
import com.example.NotesAppAPI.Exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidCredentialException.class)
    public Map<String,String> handleInvalidCredentialException(InvalidCredentialException exception){
        Map<String,String> errorMap=new HashMap<>();
        errorMap.put("error",exception.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(NoteNotFoundException.class)
    public Map<String,String> handleNoteNotFoundException(NoteNotFoundException exception){
        Map<String,String> errorMap=new HashMap<>();
        errorMap.put("error",exception.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(UserNotFoundException.class)
    public Map<String,String> handleUserNotFoundException(UserNotFoundException exception){
        Map<String,String> errorMap=new HashMap<>();
        errorMap.put("error",exception.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidUserException.class)
    public Map<String,String> handleInvalidUserFoundException(InvalidUserException exception){
        Map<String,String> errorMap=new HashMap<>();
        errorMap.put("error",exception.getMessage());
        return errorMap;
    }
}
