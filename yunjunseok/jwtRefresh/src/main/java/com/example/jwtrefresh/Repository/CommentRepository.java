package com.example.jwtrefresh.Repository;

import com.example.jwtrefresh.Domain.Comment;
import com.example.jwtrefresh.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByUser(User user);
}
