package com.giorgi.ibmapp.service;

import com.giorgi.ibmapp.domain.*;
import com.giorgi.ibmapp.integration.AiTicketDraft;
import com.giorgi.ibmapp.repository.CommentRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class  CommentLifecycleService {
    private final CommentRecordRepository commentRepository;
    private final CommentTriageService triageService;
    private final TicketCreationService ticketCreationService;
    private final AiTicketDraftMapper draftMapper;

    public CommentLifecycleService(CommentRecordRepository commentRepository, CommentTriageService triageService, TicketCreationService ticketCreationService, AiTicketDraftMapper draftMapper) {
        this.commentRepository = commentRepository;
        this.triageService = triageService;
        this.ticketCreationService = ticketCreationService;
        this.draftMapper = draftMapper;

    }
    @Transactional
    public CommentRecord registerNewComment(String authorHandle,String body){
        CommentRecord comment = new CommentRecord();

        comment.setAuthorHandle(authorHandle);
        comment.setBody(body);
        comment.setSubmittedAt(Instant.now());
        comment.setState(CommentState.RECEIVED);

        CommentRecord savedComment = commentRepository.save(comment);
        try {


            AiTicketDraft draft = triageService.analyzeComment(body);

            if (draft.isShouldCreateTicket()) {
                SupportTicket ticket = ticketCreationService.createTicket(
                        savedComment.getId(),
                        draftMapper.resolveTitle(draft),
                        draftMapper.resolveSummary(draft, body),
                        draftMapper.resolveCategory(draft),
                        draftMapper.resolvePriority(draft)
                );
                savedComment.setState(CommentState.TICKET_CREATED);
                savedComment.setLinkedTicketId(ticket.getId());

            } else {
                savedComment.setState(CommentState.NO_TICKET_CREATED);
            }
        }catch (Exception exception){
            savedComment.setState(CommentState.TRIAGE_FAILED);
        }
        return commentRepository.save(savedComment);
    }
    public List<CommentRecord> fetchAllComments() {
        return commentRepository.findAll();
    }
}
