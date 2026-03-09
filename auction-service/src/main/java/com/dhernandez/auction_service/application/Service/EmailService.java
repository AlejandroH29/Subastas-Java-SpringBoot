package com.dhernandez.auction_service.application.Service;

import com.dhernandez.auction_service.application.port.out.Email.SendEmailPort;
import com.dhernandez.auction_service.domain.model.EmailMessage;

public class EmailService {
    private final SendEmailPort sendEmailPort;

    private static final int MAX_ATTEMPTS = 3;
    private static final long INITIAL_DELAY_MS = 1000;
    public EmailService(SendEmailPort sendEmailPort){
        this.sendEmailPort = sendEmailPort;
    }

    public void sendEmailVerification(String email, String userName, int token){
        String to = email;
        String subject = "Bienvenido a Auction Service";
        String body = "<p>Hola " + userName + "</p>"
            + "<p>Tu token es: <strong>" + token + "</strong></p>";
        EmailMessage emailMessage = new EmailMessage(to, subject, body);
        sendWithRetry(emailMessage);
    }

    private void sendWithRetry(EmailMessage message) {
        long delay = INITIAL_DELAY_MS;

        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {

            try {
                sendEmailPort.send(message);
                return;
            } catch (Exception e) {
                if (attempt == MAX_ATTEMPTS) {
                    System.err.println("Failed to send email after retries: " + e.getMessage());
                    return;
                }
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                    return;
                }
                delay *= 2;
            }
        }
    }
}
