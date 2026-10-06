package com.senla.test.senlaerrorfreetext.repository;

import com.senla.test.senlaerrorfreetext.enums.TaskStatus;
import com.senla.test.senlaerrorfreetext.model.CorrectionTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CorrectionTaskRepository extends JpaRepository<CorrectionTask, UUID> {
    List<CorrectionTask> findTop10ByStatusOrderByCreatedAtAsc(TaskStatus status);
}
