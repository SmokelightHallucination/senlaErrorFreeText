package com.senla.test.senlaerrorfreetext.repository;

import model.CorrectionTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CorrectionTaskRepository extends JpaRepository<CorrectionTask, UUID> {

}
