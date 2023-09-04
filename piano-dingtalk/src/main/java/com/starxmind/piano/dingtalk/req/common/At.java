package com.starxmind.piano.dingtalk.req.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * TODO
 *
 * @author pizzalord
 * @since 1.0
 */
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
public class At {
    private List<String> atMobiles;
    private List<String> atUserIds;
    private boolean isAtAll;
}
