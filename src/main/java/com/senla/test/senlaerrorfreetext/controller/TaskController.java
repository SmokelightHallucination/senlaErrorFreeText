package com.senla.test.senlaerrorfreetext.controller;

import com.senla.test.senlaerrorfreetext.dto.CreateTaskRequest;
import com.senla.test.senlaerrorfreetext.dto.CreateTaskResponse;
import com.senla.test.senlaerrorfreetext.dto.TaskResponse;
import com.senla.test.senlaerrorfreetext.service.TaskService;
import com.senla.test.senlaerrorfreetext.service.TextCorrectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController("/api")
public class TaskController {
    private final TaskService taskService;
    private final TextCorrectionService textCorrectionService;

    @PostMapping("/correct")
    public ResponseEntity<CreateTaskResponse> createTask(
            @Valid @RequestBody CreateTaskRequest request
    ) {
        UUID id = taskService.createTask(request);
        return ResponseEntity.status
                (HttpStatus.CREATED)
                .body(new CreateTaskResponse(id));
    }

    @GetMapping("/{id}")
    public TaskResponse getTask(@PathVariable UUID id) {
        return taskService.getTask(id);
    }
}
