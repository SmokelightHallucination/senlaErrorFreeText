package com.senla.test.senlaerrorfreetext.scheduler;

import com.senla.test.senlaerrorfreetext.model.CorrectionTask;
import com.senla.test.senlaerrorfreetext.service.TaskService;
import com.senla.test.senlaerrorfreetext.service.TextCorrectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CorrectionTaskScheduler {

    private final TaskService taskService;
    private final TextCorrectionService textCorrectionService;

    @Scheduled(fixedDelay = 5000)
    public void processTasks() {
        List<CorrectionTask> tasks = taskService.getNewTasks();

        for (CorrectionTask task : tasks) {
            processTask(task);
        }
    }

    private void processTask(CorrectionTask task) {
        try {
            taskService.markAsProcessing(task);

            log.info("Started processing task: id={}", task.getId());

            String correctedText = textCorrectionService.correctText(
                    task.getOriginalText(),
                    task.getLanguage()
            );

            taskService.markAsCompleted(task, correctedText);

            log.info("Task completed: id={}", task.getId());

        } catch (Exception ex) {
            log.error("Task processing failed: id={}", task.getId(), ex);

            try {
                taskService.markAsFailed(
                        task,
                        ex.getMessage() == null
                                ? "Text correction failed"
                                : ex.getMessage()
                );
            } catch (Exception saveEx) {
                log.error("Failed to save FAILED status: id={}",
                        task.getId(), saveEx);
            }
        }
    }
}