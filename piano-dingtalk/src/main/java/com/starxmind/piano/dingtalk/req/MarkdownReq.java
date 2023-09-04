package com.starxmind.piano.dingtalk.req;

import com.starxmind.piano.dingtalk.MessageType;
import com.starxmind.piano.dingtalk.req.common.At;
import com.starxmind.piano.dingtalk.req.markdown.Markdown;
import lombok.Builder;
import lombok.Data;

/**
 * TODO
 *
 * @author pizzalord
 * @since 1.0
 */
@Builder(toBuilder = true)
@Data
public class MarkdownReq {
    @Builder.Default
    private String msgtype = MessageType.markdown.name();
    private Markdown markdown;
    private At at;
}
