package project.chatbot.GPTCounselor.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import project.chatbot.GPTCounselor.domain.Chat;
import project.chatbot.GPTCounselor.domain.Consulting;
import project.chatbot.GPTCounselor.dto.chat.request.SendChatDTO;
import project.chatbot.GPTCounselor.dto.chat.response.GptChatDTO;
import project.chatbot.GPTCounselor.dto.consulting.response.SaveSolutionDTO;
import project.chatbot.GPTCounselor.dto.gpt.request.GptRequest;
import project.chatbot.GPTCounselor.dto.gpt.request.Message;
import project.chatbot.GPTCounselor.dto.gpt.response.GptResponse;
import project.chatbot.GPTCounselor.dto.papago.PapagoResponse;
import project.chatbot.GPTCounselor.repository.ChatRepository;
import project.chatbot.GPTCounselor.repository.ConsultingRepository;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
    @Value("${CLIENT_ID}")
    private String papagoId;
    @Value("${CLIENT_SECRET}")
    private String papagoSecret;
    @Value("${gpt.secret}")
    private String gptSecretKey;
    private final ChatRepository chatRepository;
    private final ConsultingRepository consultingRepository;
    private RestTemplate restTemplate = new RestTemplate();
    @Transactional
    public Chat saveChat(Consulting consulting, String role, String chat){
        return chatRepository.save(
                Chat.builder()
                        .consulting(consulting)
                        .role(role)
                        .content(chat)
                .build());
    }
    public GptChatDTO sendMessage(SendChatDTO sendChatDTO, boolean needSolution) throws JsonProcessingException {
        Consulting consulting = consultingRepository.findById(sendChatDTO.getConsultingId())
                .orElseThrow(() -> new RuntimeException());
        log.info("컨설팅 조회 성공");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(gptSecretKey);
        log.info("gpt 헤더 설정 완료");
        HttpEntity httpEntity = new HttpEntity(makeRequest(consulting, sendChatDTO.getUserChat()), headers);
        log.info("gpt http entity 생성 완료");

        String jsonResponse = restTemplate.exchange(
                "https://api.openai.com/v1/chat/completions",
                HttpMethod.POST,
                httpEntity,
                String.class
        ).getBody();
        log.info("gpt api 응답 완료");

        ObjectMapper objectMapper = new ObjectMapper();
        GptResponse gptResponse = objectMapper.readValue(jsonResponse, GptResponse.class);
        log.info("gpt json 파싱 완료");

        String gptChat = gptResponse.getChoices().get(0).getMessage().getContent();
        gptChat= translateChat(gptChat, true);
        saveChat(consulting, "assistant", gptChat);
        log.info("채팅 저장 완료: " + gptChat);

        return new GptChatDTO(gptChat);
    }
    public String translateChat(String chat, boolean enToKo) throws JsonProcessingException {


        String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
        try {
            chat = URLEncoder.encode(chat, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("인코딩 실패", e);
        }

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", papagoId);
        requestHeaders.put("X-Naver-Client-Secret", papagoSecret);
        log.info("papago header 생성 완료");


        String responseBody = post(apiURL, requestHeaders, chat, enToKo);
        log.info("papago api response success");

        ObjectMapper objectMapper = new ObjectMapper();
        String translatedText = objectMapper.readValue(responseBody, PapagoResponse.class).getMessage().getResult().getTranslatedText();
        log.info("papago json parsing 완료");

        return translatedText;
    }
    private static String post(String apiUrl, Map<String, String> requestHeaders, String text, boolean en){
        HttpURLConnection con = connect(apiUrl);
        String changeEn = "source=en&target=ko&text=" + text; //원본언어: 한국어 (ko) -> 목적언어: 영어 (en)
        String changeKo = "source=ko&target=en&text=" + text; //원본언어: 영어 (en) -> 목적언어: 한국어 (ko)
        try {
            con.setRequestMethod("POST");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            con.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                if (en) wr.write(changeEn.getBytes());
                else wr.write(changeKo.getBytes());
                wr.flush();
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 응답
                return readBody(con.getInputStream());
            } else {  // 에러 응답
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }
    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }

    public GptRequest makeRequest(Consulting consulting, String userChat){
        saveChat(consulting, "user", userChat);
        List<Message> messages = chatRepository.findAllByConsulting(consulting).stream()
                .map(chat -> new Message(chat.getRole(), chat.getContent()))
                .toList();
        return GptRequest.builder()
                .messages(messages)
                .temperature(0.5)
                .build();
    }
    public GptRequest makeSolutionRequest(Consulting consulting){
        List<Message> messages = chatRepository.findAllByConsulting(consulting).stream()
                .map(chat -> new Message(chat.getRole(), chat.getContent()))
                .collect(Collectors.toList());
        messages.add(new Message("user", "너와 나의 채팅을 보고 3줄 정도로 요약하고 솔루션을 제시해줘"));
        return GptRequest.builder()
                .messages(messages)
                .temperature(0.5)
                .build();
    }
    @Transactional
    public SaveSolutionDTO makeSolution(Consulting consulting) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(gptSecretKey);
        log.info("gpt 헤더 설정 완료");
        HttpEntity httpEntity = new HttpEntity(makeSolutionRequest(consulting), headers);
        log.info("gpt http entity 생성 완료");

        String jsonResponse = restTemplate.exchange(
                "https://api.openai.com/v1/chat/completions",
                HttpMethod.POST,
                httpEntity,
                String.class
        ).getBody();
        log.info("gpt api 응답 완료");

        ObjectMapper objectMapper = new ObjectMapper();
        GptResponse gptResponse = objectMapper.readValue(jsonResponse, GptResponse.class);
        log.info("gpt json 파싱 완료");

        String gptChat = gptResponse.getChoices().get(0).getMessage().getContent();
        String korSolution = translateChat(gptChat, true);
        consulting.saveSolution(korSolution);
        consultingRepository.save(consulting);
        return new SaveSolutionDTO(korSolution);
    }
}
