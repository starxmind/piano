package com.starxmind.piano.dingtalk.req;

import com.starxmind.piano.dingtalk.MessageType;
import com.starxmind.piano.dingtalk.req.text.Text;
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
public class TextReq {
    @Builder.Default
    private String msgtype = MessageType.text.name();
    private Text text;
}
