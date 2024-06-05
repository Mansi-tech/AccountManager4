package com.example.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}