package com.example.jwtrefresh.Service;

import com.example.jwtrefresh.Domain.Comment;
import com.example.jwtrefresh.Domain.User;
import com.example.jwtrefresh.Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.jwtrefresh.Dto.CommentResponseDto;
import java.util.stream.Collectors;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
    // 댓글 등록
    @Transactional
    public Comment createComment(User user, String content) {

        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("댓글 내용은 비어있을 수 없습니다!");
        }

        Comment comment = Comment.builder()
                .user(user)
                .content(content)
                .build();
        return commentRepository.save(comment);
    }
    // 댓글 수정
    @Transactional
    public void updateComment(Long commentId, String content, User authenticatedUser) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        if (!comment.getUser().equals(authenticatedUser)) {
            throw new IllegalStateException("댓글 수정 권한이 없습니다!");
        }

        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("댓글 내용은 비어있을 수 없습니다!");
        }

        comment.setContent(content);

        commentRepository.save(comment);
    }
    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId, User user) {
        if (user == null) {
            throw new IllegalArgumentException("유저 정보가 없습니다.");
        }

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다!"));

        if (!comment.getUser().equals(user)) {
            throw new IllegalArgumentException("댓글 삭제 권한이 없습니다!");
        }

        commentRepository.delete(comment);
    }
    // 모든 댓글 조회
    public List<CommentResponseDto> getAllComments() {
        return commentRepository.findAll().stream()
                .map(comment -> {
                    CommentResponseDto dto = new CommentResponseDto();
                    dto.setId(comment.getId());
                    dto.setContent(comment.getContent());
                    dto.setUserId(comment.getUser().getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
