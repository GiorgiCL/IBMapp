package com.giorgi.ibmapp.repository;

import com.giorgi.ibmapp.domain.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long> {
}
