package project.chatbot.GPTCounselor.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import project.chatbot.GPTCounselor.dto.gpt.request.GptRequest;
import project.chatbot.GPTCounselor.dto.gpt.request.Message;
import project.chatbot.GPTCounselor.dto.gpt.response.GptResponse;
import project.chatbot.GPTCounselor.repository.ChatRepository;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class ChatService {
    @Value("${gpt.secret}")
    private String secretKey;
    private final ChatRepository chatRepository;
    private RestTemplate restTemplate = new RestTemplate();
    public GptResponse sendMessage() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(secretKey);
        HttpEntity httpEntity = new HttpEntity(makeRequest(), headers);

        String jsonResponse = restTemplate.exchange(
                "https://api.openai.com/v1/chat/completions",
                HttpMethod.POST,
                httpEntity,
                String.class
        ).getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        GptResponse gptResponse = objectMapper.readValue(jsonResponse, GptResponse.class);
        return gptResponse;
    }
    private GptRequest makeRequest(){
        Message system = Message.builder()
                .role("system")
                .content("You are a helpful assistant")
                .build();
        Message user = Message.builder()
                .role("user")
                .content("hello! my name is lim jeong woo")
                .build();
        return GptRequest.builder()
                .messages(Arrays.asList(system, user))
                .build();
    }
}
