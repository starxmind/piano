package com.starxmind.piano.email.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder(toBuilder = true)
@Data
public class EmailSenderRequest {
    private String to;
    private String subject;
    private String content;
    private boolean html;
    private List<Attachment> attachments;
}
