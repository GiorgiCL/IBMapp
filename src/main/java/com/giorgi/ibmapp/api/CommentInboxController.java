package com.giorgi.ibmapp.api;

import com.giorgi.ibmapp.domain.CommentRecord;
import com.giorgi.ibmapp.service.CommentLifecycleService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
