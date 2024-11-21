package com.example.jwtrefresh.Controller;

import com.example.jwtrefresh.Domain.User;
import com.example.jwtrefresh.Dto.CommentAddDto;
import com.example.jwtrefresh.Dto.CommentResponseDto;
import com.example.jwtrefresh.Jwt.TokenProvider;
import com.example.jwtrefresh.Repository.UserRepository;
import com.example.jwtrefresh.Service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    @Autowired
    public CommentController(CommentService commentService, TokenProvider tokenProvider, UserRepository userRepository) {
        this.commentService = commentService;
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
    }
    // 권한 확인
    private User getAuthenticatedUser(HttpServletRequest request) {
        String token = tokenProvider.resolveToken(request);
        if (token == null || !tokenProvider.validateToken(token)) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }
        Authentication authentication = tokenProvider.getAuthentication(token);
        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("유효한 사용자를 찾을 수 없습니다."));
    }
    // 댓글 등록
    @PostMapping
    public ResponseEntity<String> createComment(HttpServletRequest request, @RequestBody CommentAddDto commentAddDto) {
        User user = getAuthenticatedUser(request);
        commentService.createComment(user, commentAddDto.getContent());
        return ResponseEntity.ok("댓글이 등록되었습니다!");
    }
    // 모든 댓글 목록 조회
    @GetMapping("/all")
    public ResponseEntity<List<CommentResponseDto>> getAllComments() {
        List<CommentResponseDto> comments = commentService.getAllComments();
        return ResponseEntity.ok(comments);
    }
    // 댓글 수정
    @PutMapping("/{id}")
    public ResponseEntity<String> updateComment(HttpServletRequest request, @PathVariable Long id, @RequestBody CommentAddDto commentAddDto) {
        User user = getAuthenticatedUser(request);
        commentService.updateComment(id, commentAddDto.getContent(), user);
        return ResponseEntity.ok("댓글이 수정되었습니다!");
    }
    // 댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id, HttpServletRequest request) {
        User user = getAuthenticatedUser(request);
        commentService.deleteComment(id, user);
        return ResponseEntity.ok("댓글이 삭제되었습니다!");
    }
}
