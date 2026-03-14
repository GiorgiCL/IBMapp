package com.giorgi.ibmapp.service;

import com.giorgi.ibmapp.domain.*;
import com.giorgi.ibmapp.repository.CommentRecordRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class  CommentLifecycleService {
    private final CommentRecordRepository commentRepository;
    private final CommentTriageService triageService;
    private final TicketCreationService ticketCreationService;

    public CommentLifecycleService(CommentRecordRepository commentRepository, CommentTriageService triageService, TicketCreationService ticketCreationService) {
        this.commentRepository = commentRepository;
        this.triageService = triageService;
        this.ticketCreationService = ticketCreationService;

    }
    public CommentRecord registerNewComment(String authorHandle,String body){
        CommentRecord comment = new CommentRecord();

        comment.setAuthorHandle(authorHandle);
        comment.setBody(body);
        comment.setSubmittedAt(Instant.now());
        comment.setState(CommentState.RECEIVED);

        CommentRecord savedComment = commentRepository.save(comment);
        boolean shouldCreateTicket = triageService.shouldCreateTicket(body);
        if(shouldCreateTicket){
            SupportTicket ticket = ticketCreationService.createTicket(
                    savedComment.getId(),
                    "AI generated support case",
                    body,
                    TicketCategory.OTHER,
                    TicketPriority.MEDIUM
            );
            savedComment.setState(CommentState.TICKET_CREATED);
            savedComment.setLinkedTicketId(ticket.getId());
        }else{
            savedComment.setState(CommentState.NO_TICKET_CREATED);
        }
        return commentRepository.save(savedComment);
    }
    public List<CommentRecord> fetchAllComments() {
        return commentRepository.findAll();
    }
}
