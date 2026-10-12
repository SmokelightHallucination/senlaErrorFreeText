package com.senla.test.senlaerrorfreetext.service.impl;

import com.senla.test.senlaerrorfreetext.client.YandexSpellerClient;
import com.senla.test.senlaerrorfreetext.dto.SpellerError;
import com.senla.test.senlaerrorfreetext.enums.Language;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskCorrectionServiceImplTest {

    @Test
    void shouldCorrectText() {
        YandexSpellerClient client = mock(YandexSpellerClient.class);

        SpellerError error = new SpellerError(
                1, 0, 0, 0, 6,
                "Превет", List.of("Привет")
        );

        when(client.checkText("Превет мир", "ru", 0))
                .thenReturn(List.of(error));

        TaskCorrectionServiceImpl service =
                new TaskCorrectionServiceImpl(client);

        String result = service.correctText(
                "Превет мир", Language.RU
        );

        assertEquals("Привет мир", result);

        verify(client).checkText("Превет мир", "ru", 0);
    }

    @Test
    void shouldPropagateYandexError() {
        YandexSpellerClient client = mock(YandexSpellerClient.class);

        when(client.checkText("Превет мир", "ru", 0))
                .thenThrow(new RuntimeException("Yandex unavailable"));

        TaskCorrectionServiceImpl service =
                new TaskCorrectionServiceImpl(client);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.correctText("Превет мир", Language.RU)
        );

        assertEquals("Yandex unavailable", exception.getMessage());
    }
}