package com.senla.test.senlaerrorfreetext.client;

import com.senla.test.senlaerrorfreetext.dto.SpellerError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.List;

@Slf4j
@Component
public class YandexSpellerClient {
    private final RestClient restClient;

    public YandexSpellerClient(RestClient.Builder builder) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(3000);
        requestFactory.setReadTimeout(10000);

        this.restClient = builder
                .requestFactory(requestFactory)
                .baseUrl("https://speller.yandex.net/services/spellservice.json")
                .build();
    }

    public List<SpellerError> checkText(
            String text,
            String language,
            int options
    ) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("text", text);
        params.add("lang", language);
        params.add("options", String.valueOf(options));

        log.debug("Checking spelling: length={}, language={}, options={}",
                text.length(), language, options);

        List<List<SpellerError>> response = restClient.post()
                .uri("/checkTexts")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(params)
                .retrieve()
                .body(new ParameterizedTypeReference<List<List<SpellerError>>>() {});

        if (response == null || response.size() != 1 || response.get(0) == null) {
            throw new IllegalStateException(
                    "Unexpected response from Yandex Speller"
            );
        }

        return response.get(0);
    }

}
