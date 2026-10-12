package com.senla.test.senlaerrorfreetext.scheduler;

import com.senla.test.senlaerrorfreetext.enums.Language;
import com.senla.test.senlaerrorfreetext.model.CorrectionTask;
import com.senla.test.senlaerrorfreetext.service.TaskService;
import com.senla.test.senlaerrorfreetext.service.TextCorrectionService;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.UUID;
import static org.mockito.Mockito.*;

class CorrectionTaskSchedulerTest {

    @Test
    void shouldCompleteTaskSuccessfully() {
        TaskService taskService = mock(TaskService.class);
        TextCorrectionService correctionService =
                mock(TextCorrectionService.class);

        CorrectionTaskScheduler scheduler =
                new CorrectionTaskScheduler(taskService, correctionService);

        CorrectionTask task = createTask();

        when(taskService.getNewTasks()).thenReturn(List.of(task));

        when(correctionService.correctText("Превет мир", Language.RU))
                .thenReturn("Привет мир");

        scheduler.processTasks();

        verify(taskService).markAsProcessing(task);
        verify(taskService).markAsCompleted(task, "Привет мир");
        verify(taskService, never()).markAsFailed(any(), anyString());
    }

    @Test
    void shouldMarkTaskAsFailed() {
        TaskService taskService = mock(TaskService.class);
        TextCorrectionService correctionService =
                mock(TextCorrectionService.class);

        CorrectionTaskScheduler scheduler =
                new CorrectionTaskScheduler(taskService, correctionService);

        CorrectionTask task = createTask();

        when(taskService.getNewTasks()).thenReturn(List.of(task));

        when(correctionService.correctText("Превет мир", Language.RU))
                .thenThrow(new RuntimeException("Yandex unavailable"));

        scheduler.processTasks();

        verify(taskService).markAsProcessing(task);
        verify(taskService).markAsFailed(task, "Yandex unavailable");
        verify(taskService, never()).markAsCompleted(any(), anyString());
    }

    @Test
    void shouldSkipProcessingWhenNoTasksExist() {
        TaskService taskService = mock(TaskService.class);
        TextCorrectionService correctionService =
                mock(TextCorrectionService.class);

        CorrectionTaskScheduler scheduler =
                new CorrectionTaskScheduler(taskService, correctionService);

        when(taskService.getNewTasks()).thenReturn(List.of());

        scheduler.processTasks();

        verifyNoInteractions(correctionService);
        verify(taskService, never()).markAsProcessing(any());
    }

    private CorrectionTask createTask() {
        return CorrectionTask.builder()
                .id(UUID.randomUUID())
                .originalText("Превет мир")
                .language(Language.RU)
                .build();
    }
}