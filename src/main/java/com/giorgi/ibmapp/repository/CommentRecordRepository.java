package com.giorgi.ibmapp.repository;

import com.giorgi.ibmapp.domain.CommentRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRecordRepository extends JpaRepository<CommentRecord, Long> {

}
