package org.rafs.emailservice.Dto;

public record EmailRequest(String to, String subject, String body, String sender) {
}
