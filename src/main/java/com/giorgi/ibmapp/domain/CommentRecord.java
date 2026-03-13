package com.giorgi.ibmapp.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
public class CommentRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String authorHandle;

    @Column(length = 2000)
    private String body;

    private Instant submittedAt;

    @Enumerated(EnumType.STRING)
    private CommentState state;

    private Long linkedTicketId;
}
