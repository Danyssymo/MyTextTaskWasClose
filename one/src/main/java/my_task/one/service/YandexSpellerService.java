package my_task.one.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import my_task.one.bean.Lang;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;


@Service
public class YandexSpellerService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String YANDEX_SPELLER_URL = "https://speller.yandex.net/services/spellservice.json/checkText";

    public String checkText(String text, Lang lang) {

        if (!validationText(text)){
            
        }
        int options = 0;
        if (text.matches(".*\\d.*")) {
            options |= 2;
        }
        if (text.matches(".*(https?://|www\\.).*")) {
            options |= 4;
        }

        URI uri = UriComponentsBuilder.fromHttpUrl(YANDEX_SPELLER_URL)
                .queryParam("text", text)
                .queryParam("lang", lang)
                .queryParam("format", "plain")
                .queryParam("options", options)
                .build()
                .toUri();

        String response = restTemplate.getForObject(uri, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        String correctedText = text;
        try {
            JsonNode rootNode = objectMapper.readTree(response);
            for (JsonNode errorNode : rootNode) {
                String wrongWord = errorNode.get("word").asText();
                JsonNode suggestionsNode = errorNode.get("s");
                if (suggestionsNode != null && suggestionsNode.size() > 0) {
                    String suggestion = suggestionsNode.get(0).asText();
                    correctedText = correctedText.replace(wrongWord, suggestion);
                }
            }
        } catch (JsonProcessingException e) {

            throw new RuntimeException(e);
        }
        return correctedText;
    }

    private boolean validationText(String text){
        if (text.length() < 3) {
            return false;
        }
        if (text.matches("[\\W\\d]+")) {
            return false;
        }
        return true;
    }

}
