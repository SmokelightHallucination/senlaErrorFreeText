package com.senla.test.senlaerrorfreetext.controller;

import com.senla.test.senlaerrorfreetext.service.TaskService;
import com.senla.test.senlaerrorfreetext.service.TextCorrectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController("/api")
public class TaskController {
    private final TaskService taskService;
    private final TextCorrectionService textCorrectionService;
}
