package com.giorgi.ibmapp.api;

import com.giorgi.ibmapp.domain.CommentRecord;
import com.giorgi.ibmapp.service.CommentLifecycleService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentInboxController {
    private final CommentLifecycleService commentService;

    public CommentInboxController(CommentLifecycleService commentService) {
        this.commentService = commentService;
    }
    @PostMapping
    public CommentRecord submitComment(@Valid @RequestBody CommentSubmissionRequest request) {

        return commentService.registerNewComment(
                request.getAuthorHandle(),
                request.getBody()
        );
    }
    @GetMapping
    public List<CommentRecord> getAllComments() {
        return commentService.fetchAllComments();
    }

}
