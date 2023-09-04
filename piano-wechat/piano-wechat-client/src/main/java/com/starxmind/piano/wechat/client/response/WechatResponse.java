package com.starxmind.piano.wechat.client.response;

import com.starxmind.piano.wechat.client.exceptions.WechatClientException;
import lombok.Data;

/**
 * TODO
 *
 * @author pizzalord
 * @since 1.0
 */
@Data
public class WechatResponse {
    private int errcode;
    private String errmsg;

    public void ok() {
        if (this.errcode != 0) {
            throw new WechatClientException(
                    String.format(
                            "Fatal: an error occurred while fetching wechat server, error code: %s, error message: %s",
                            this.errcode, this.errmsg)
            );
        }
    }
}
