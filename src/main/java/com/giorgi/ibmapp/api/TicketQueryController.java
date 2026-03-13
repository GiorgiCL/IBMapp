package com.giorgi.ibmapp.api;

import com.giorgi.ibmapp.domain.SupportTicket;
import com.giorgi.ibmapp.repostiory.SupportTicketRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketQueryController {
    private final SupportTicketRepository supportRepository;

    public TicketQueryController(SupportTicketRepository supportRepository) {
        this.supportRepository = supportRepository;
    }
    @GetMapping
    public List<SupportTicket> getAllTickets() {
        return supportRepository.findAll();
    }
    @GetMapping("/{ticketId}")
    public SupportTicket getTicketById(@PathVariable Long ticketId) {
        return supportRepository.findById(ticketId).orElse(null);
    }
}

