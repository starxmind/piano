package com.starxmind.piano.sms.guoyangyun.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * TODO
 *
 * @author pizzalord
 * @since 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GuoyangyunSmsResponse {
    private String code;
    private String msg;
    private String smsid;
    private String balance;
}
