package com.senla.test.senlaerrorfreetext.service;

import com.senla.test.senlaerrorfreetext.dto.CreateTaskRequest;
import com.senla.test.senlaerrorfreetext.dto.TaskResponse;
import com.senla.test.senlaerrorfreetext.model.CorrectionTask;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    UUID createTask(CreateTaskRequest request);

    TaskResponse getTask(UUID id);

    List<CorrectionTask> getNewTasks();

    void markAsProcessing(CorrectionTask task);

    void markAsCompleted(CorrectionTask task, String correctedText);

    void markAsFailed(CorrectionTask task, String errorMessage);
}
