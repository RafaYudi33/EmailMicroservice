package org.rafs.emailservice.DTO;

public record EmailRequest(String to, String subject, String body, String sender) {

}
