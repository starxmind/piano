package com.starxmind.piano.dingtalk;

/**
 * TODO
 *
 * @author pizzalord
 * @since 1.0
 */
public abstract class DingTalkConstants {
    public static final String WEBHOOK_URL = "https://oapi.dingtalk.com/robot/send?access_token=${accessToken}";
    public static final String WEBHOOK_URL_WITH = WEBHOOK_URL + "&timestamp=${timestamp}&sign=${sign}";
}
