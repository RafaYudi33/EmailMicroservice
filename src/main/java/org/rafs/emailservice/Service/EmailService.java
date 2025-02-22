package org.rafs.emailservice.Service;

import org.rafs.emailservice.DTO.EmailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;


public class EmailService {
    private final SesClient sesClient;
    Logger logger = LoggerFactory.getLogger(EmailService.class);

    public EmailService() {
        this.sesClient = SesClient.create();
    }

    public void sendEmail(EmailRequest emailRequestDto) {
        try {
            Destination destination = Destination.builder()
                    .toAddresses(emailRequestDto.to())
                    .build();

            Content content = Content.builder()
                    .data(emailRequestDto.body())
                    .build();

            Content sub = Content.builder()
                    .data(emailRequestDto.subject())
                    .build();

            Body body = Body.builder()
                    .text(content)
                    .build();

            Message msg = Message.builder()
                    .subject(sub)
                    .body(body)
                    .build();

            SendEmailRequest emailRequest = SendEmailRequest.builder()
                    .destination(destination)
                    .message(msg)
                    .source(emailRequestDto.sender())
                    .build();

            sesClient.sendEmail(emailRequest);
            logger.info("✅ Email enviado com sucesso para: {}", emailRequestDto.to());
        } catch (Exception e) {
            logger.error("❌ Erro ao enviar email: {}", e.getMessage());
        }
    }

}
