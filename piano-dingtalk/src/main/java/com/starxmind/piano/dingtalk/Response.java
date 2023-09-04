package com.starxmind.piano.dingtalk;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 钉钉返回
 *
 * @author pizzalord
 * @since 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Response {
    private int errcode;
    private String errmsg;
}
