package com.senla.test.senlaerrorfreetext.service.impl;

import com.senla.test.senlaerrorfreetext.client.YandexSpellerClient;
import com.senla.test.senlaerrorfreetext.dto.SpellerError;
import com.senla.test.senlaerrorfreetext.enums.Language;
import com.senla.test.senlaerrorfreetext.repository.CorrectionTaskRepository;
import com.senla.test.senlaerrorfreetext.service.TextCorrectionService;
import com.senla.test.senlaerrorfreetext.utils.SpellerOptionsUtils;
import com.senla.test.senlaerrorfreetext.utils.TextChunkUtils;
import com.senla.test.senlaerrorfreetext.utils.TextCorrectionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskCorrectionServiceImpl implements TextCorrectionService {
    private final YandexSpellerClient  yandexSpellerClient;

    @Override
    public String correctText(String text, Language language) {
        int options = SpellerOptionsUtils.calculateOptions(text);
        List<String> chunks = TextChunkUtils.splitText(text);

        StringBuilder result = new StringBuilder();

        for (String chunk : chunks) {
            List<SpellerError> errors = yandexSpellerClient.checkText(
                    chunk,
                    language.getApiValue(),
                    options
            );

            result.append(
                    TextCorrectionUtils.applyCorrections(chunk, errors)
            );
        }

        log.info("Text correction completed: length={}, chunks={}",
                text.length(), chunks.size());

        return result.toString();
    }
}
