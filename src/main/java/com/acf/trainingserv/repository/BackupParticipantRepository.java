package com.acf.trainingserv.repository;

import com.acf.trainingserv.model.BackupParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BackupParticipantRepository extends JpaRepository<BackupParticipant, Long> {

}
