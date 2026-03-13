package com.giorgi.ibmapp.repostiory;

import com.giorgi.ibmapp.domain.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long> {
}
