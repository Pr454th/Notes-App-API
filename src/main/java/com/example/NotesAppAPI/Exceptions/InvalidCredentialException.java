package com.example.NotesAppAPI.Exceptions;

public class InvalidCredentialException extends Exception{
    public InvalidCredentialException(String message){
        super(message);
    }
}
