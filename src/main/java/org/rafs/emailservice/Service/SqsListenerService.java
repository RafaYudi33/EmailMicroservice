package org.rafs.emailservice.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.rafs.emailservice.Dto.EmailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.util.List;

@Service
public class SqsListenerService {
    private final SqsClient sqsClient;
    private final EmailService emailService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    Logger logger = LoggerFactory.getLogger(SqsListenerService.class);

    @Value("${aws.sqs.queue.url}")
    private String queueUrl;

    public SqsListenerService(SqsClient sqsClient, EmailService emailService) {
        this.sqsClient = sqsClient;
        this.emailService = emailService;
    }

    @PostConstruct
    public void startListening(){
        new Thread(() -> {
            while(true){
                ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                        .queueUrl(queueUrl)
                        .maxNumberOfMessages(10)
                        .waitTimeSeconds(20)
                        .build();

                List<Message> sqsMessages = sqsClient.receiveMessage(receiveMessageRequest)
                        .messages();

                for(Message message : sqsMessages){

                    EmailRequest emailRequest;
                    try {
                        emailRequest = objectMapper.readValue(message.body(), EmailRequest.class);
                        this.emailService.sendEmail(emailRequest);
                        deleteMessages(message);
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }
                }
            }
        }).start();
    }

    public void deleteMessages(Message message){
        DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                .queueUrl(queueUrl)
                .receiptHandle(message.receiptHandle())
                .build();

        sqsClient.deleteMessage(deleteMessageRequest);
    }
}