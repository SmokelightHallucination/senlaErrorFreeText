package com.senla.test.senlaerrorfreetext.repository;

import com.senla.test.senlaerrorfreetext.enums.TaskStatus;
import com.senla.test.senlaerrorfreetext.model.CorrectionTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface CorrectionTaskRepository extends JpaRepository<CorrectionTask, UUID> {
    List<CorrectionTask> findTop10ByStatusOrderByCreatedAtAsc(TaskStatus status);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
            update CorrectionTask t
            set t.status = :newStatus, t.updatedAt = :updatedAt,
                t.correctedText = null, t.errorMessage = null
            where t.status = :processingStatus
            """)
    int resetProcessingTasks(@Param("processingStatus") TaskStatus processingStatus,
                             @Param("newStatus") TaskStatus newStatus,
                             @Param("updatedAt") LocalDateTime updatedAt);
}
