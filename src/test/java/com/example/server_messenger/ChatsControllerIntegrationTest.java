package com.example.server_messenger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChatsControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testCreateChatAndSendMessage() {
        String url = "/chats/createChatAndSendMessage";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        Map<String, String> params = new HashMap<>();
        params.put("chatUserOwner", "user1");
        params.put("otherUser", "user2");
        params.put("messageText", "Привет, как дела?");
        params.put("timeCreated", "2024-12-14T15:30:00");

        HttpEntity<Map<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = testRestTemplate.postForEntity(url, request, String.class);

        // Проверяем статус и ответ
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Чаты и сообщения успешно созданы.", response.getBody());
    }
}
