package com.senla.test.senlaerrorfreetext.service.impl;

import com.senla.test.senlaerrorfreetext.dto.CreateTaskRequest;
import com.senla.test.senlaerrorfreetext.enums.Language;
import com.senla.test.senlaerrorfreetext.enums.TaskStatus;
import com.senla.test.senlaerrorfreetext.exception.InvalidTextException;
import com.senla.test.senlaerrorfreetext.exception.TaskNotFoundException;
import com.senla.test.senlaerrorfreetext.model.CorrectionTask;
import com.senla.test.senlaerrorfreetext.repository.CorrectionTaskRepository;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    @Test
    void shouldCreateTaskWithNewStatus() {
        CorrectionTaskRepository repository =
                mock(CorrectionTaskRepository.class);

        TaskServiceImpl service = new TaskServiceImpl(repository);

        UUID id = service.createTask(
                new CreateTaskRequest("Превет мир", Language.RU)
        );

        assertNotNull(id);

        verify(repository).save(argThat(task ->
                task.getId().equals(id)
                        && task.getStatus() == TaskStatus.NEW
                        && task.getOriginalText().equals("Превет мир")
                        && task.getLanguage() == Language.RU
        ));
    }

    @Test
    void shouldRejectTextWithoutLetters() {
        CorrectionTaskRepository repository =
                mock(CorrectionTaskRepository.class);

        TaskServiceImpl service = new TaskServiceImpl(repository);

        assertThrows(
                InvalidTextException.class,
                () -> service.createTask(
                        new CreateTaskRequest("123!!!", Language.RU)
                )
        );

        verify(repository, never()).save(any());
    }

    @Test
    void shouldThrowWhenTaskNotFound() {
        CorrectionTaskRepository repository =
                mock(CorrectionTaskRepository.class);

        TaskServiceImpl service = new TaskServiceImpl(repository);

        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(
                TaskNotFoundException.class,
                () -> service.getTask(id)
        );
    }

    @Test
    void shouldMarkTaskAsCompleted() {
        CorrectionTaskRepository repository =
                mock(CorrectionTaskRepository.class);

        TaskServiceImpl service = new TaskServiceImpl(repository);

        CorrectionTask task = CorrectionTask.builder()
                .id(UUID.randomUUID())
                .status(TaskStatus.PROCESSING)
                .originalText("Превет мир")
                .build();

        service.markAsCompleted(task, "Привет мир");

        assertEquals(TaskStatus.COMPLETED, task.getStatus());
        assertEquals("Привет мир", task.getCorrectedText());
        assertNotNull(task.getUpdatedAt());

        verify(repository).save(task);
    }
}