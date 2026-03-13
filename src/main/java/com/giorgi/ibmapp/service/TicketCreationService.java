package com.giorgi.ibmapp.service;

import com.giorgi.ibmapp.domain.SupportTicket;
import com.giorgi.ibmapp.domain.TicketCategory;
import com.giorgi.ibmapp.domain.TicketPriority;
import com.giorgi.ibmapp.repostiory.SupportTicketRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TicketCreationService {
private final SupportTicketRepository ticketRepository;

public TicketCreationService(SupportTicketRepository ticketRepository) {
    this.ticketRepository = ticketRepository;
}
public SupportTicket createTicket(
        Long commentId,
        String title,
        String summary,
        TicketCategory category,
        TicketPriority priority
){
    SupportTicket ticket = new SupportTicket();

            ticket.setSourceCommentId(commentId);
            ticket.setTitle(title);
            ticket.setSummary(summary);
            ticket.setCategory(category);
            ticket.setPriority(priority);
            ticket.setCreatedAt(Instant.now());

            return ticketRepository.save(ticket);

}
}
