package com.dhernandez.auction_service.infraestructure.persistence.adapter.Email;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.dhernandez.auction_service.application.port.out.Email.SendEmailPort;
import com.dhernandez.auction_service.domain.model.EmailMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;

@Component
public class EmailApiAdapter implements SendEmailPort{
    @Value("${resend.api.key}")
    private String apiKey;

    @Value("${resend.api.url}")
    private String apiUrl;

    @Value("${resend.from.email}")
    private String fromEmail;

    private final WebClient webClient = WebClient.create();

    @Override
    public void send(EmailMessage emailMessage) {
        Map<String, Object> body = new HashMap<>();
        body.put("from", fromEmail);
        body.put("to", List.of(emailMessage.getTo()));
        body.put("subject", emailMessage.getSubject());
        body.put("html", emailMessage.getBody());

        webClient.post()
        .uri(apiUrl)
        .header("Authorization", "Bearer " + apiKey)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(body)
        .retrieve()
        .toBodilessEntity()
        .block();
    }
    
}
