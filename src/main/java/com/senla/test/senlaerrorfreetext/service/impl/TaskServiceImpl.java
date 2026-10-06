package com.senla.test.senlaerrorfreetext.service.impl;

import com.senla.test.senlaerrorfreetext.dto.CreateTaskRequest;
import com.senla.test.senlaerrorfreetext.dto.TaskResponse;
import com.senla.test.senlaerrorfreetext.enums.TaskStatus;
import com.senla.test.senlaerrorfreetext.repository.CorrectionTaskRepository;
import com.senla.test.senlaerrorfreetext.service.TaskService;
import lombok.RequiredArgsConstructor;
import com.senla.test.senlaerrorfreetext.model.CorrectionTask;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final CorrectionTaskRepository repository;

    @Override
    public UUID createTask(CreateTaskRequest request) {
        CorrectionTask task = CorrectionTask.builder()
                .id(UUID.randomUUID())
                .originalText(request.text())
                .language(request.language())
                .status(TaskStatus.NEW)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        repository.save(task);

        return task.getId();
    }

    @Override
    public TaskResponse getTask(UUID id) {
        CorrectionTask task = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        return new TaskResponse(
                task.getStatus(),
                task.getCorrectedText(),
                task.getErrorMessage()
        );
    }

    @Override
    public List<CorrectionTask> getNewTasks() {
        return repository.findTop10ByStatusOrderByCreatedAtAsc(TaskStatus.NEW);
    }

    @Override
    public void markAsProcessing(CorrectionTask task) {
        task.setStatus(TaskStatus.PROCESSING);
        task.setUpdatedAt(LocalDateTime.now());

        repository.save(task);
    }

    @Override
    public void markAsCompleted(CorrectionTask task, String correctedText) {
        task.setCorrectedText(correctedText);
        task.setStatus(TaskStatus.COMPLETED);
        task.setUpdatedAt(LocalDateTime.now());

        repository.save(task);
    }

    @Override
    public void markAsFailed(CorrectionTask task, String errorMessage) {
        task.setStatus(TaskStatus.FAILED);
        task.setErrorMessage(errorMessage);
        task.setUpdatedAt(LocalDateTime.now());

        repository.save(task);
    }
}
