package org.rafs.emailservice.Service;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.rafs.emailservice.DTO.EmailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqsLambdaHandler implements RequestHandler<SQSEvent, String> {
    private final EmailService emailService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(SqsLambdaHandler.class);

    public SqsLambdaHandler() {
        this.emailService = new EmailService();
    }

    @Override
    public String handleRequest(SQSEvent event, Context context) {
        for (SQSEvent.SQSMessage message : event.getRecords()) {
            try {
                logger.info("📥 Mensagem recebida: {}", message.getBody());
                EmailRequest emailRequest = objectMapper.readValue(message.getBody(), EmailRequest.class);
                emailService.sendEmail(emailRequest);
                logger.info("✅ Email enviado com sucesso para: {}", emailRequest.to());
            } catch (Exception e) {
                throw new RuntimeException("Envio falhou");
            }
        }
        return null;
    }


}
