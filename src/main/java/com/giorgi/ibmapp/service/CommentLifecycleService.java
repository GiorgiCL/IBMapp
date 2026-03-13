package com.giorgi.ibmapp.service;

import com.giorgi.ibmapp.domain.CommentRecord;
import com.giorgi.ibmapp.domain.CommentState;
import com.giorgi.ibmapp.repostiory.CommentRecordRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class CommentLifecycleService {
    private final CommentRecordRepository commentRepository;

    public CommentLifecycleService(CommentRecordRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
    public CommentRecord registerNewComment(String authorHandle,String body){
        CommentRecord comment = new CommentRecord();

        comment.setAuthorHandle(authorHandle);
        comment.setBody(body);
        comment.setSubmittedAt(Instant.now());
        comment.setState(CommentState.RECEIVED);

        return commentRepository.save(comment);
    }
}
