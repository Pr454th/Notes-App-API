package com.example.NotesAppAPI.Repositories;

import com.example.NotesAppAPI.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
}
