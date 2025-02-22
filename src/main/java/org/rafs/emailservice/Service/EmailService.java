package org.rafs.emailservice.Service;

import org.rafs.emailservice.Dto.EmailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

@Service
public class EmailService {

    private final SesClient sesClient;
    Logger logger = LoggerFactory.getLogger(EmailService.class);

    public EmailService(SesClient sesClient) {
        this.sesClient = sesClient;
    }

    public void sendEmail(org.rafs.emailservice.Dto.EmailRequest emailRequestDto) {

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
        logger.info("Email enviado com sucesso para: {} ", emailRequestDto.to());
    }
}