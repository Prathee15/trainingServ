package com.acf.trainingserv.repository;

import com.acf.trainingserv.model.Passkey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasskeyRepository extends JpaRepository<Passkey, Long> {
}

