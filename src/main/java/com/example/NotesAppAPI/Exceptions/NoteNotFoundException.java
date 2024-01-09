package com.example.NotesAppAPI.Exceptions;

public class NoteNotFoundException extends Exception{
    public NoteNotFoundException(String message){
        super(message);
    }
}
