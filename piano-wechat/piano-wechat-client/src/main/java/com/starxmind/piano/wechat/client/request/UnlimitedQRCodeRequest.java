package com.starxmind.piano.wechat.client.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class UnlimitedQRCodeRequest {
    private String page;

    private String scene;

    @Builder.Default
    private boolean check_path = true;

    /**
     * 可选值: 正式版为 "release"，体验版为 "trial"，开发版为 "develop"。默认是正式版
     */
    private String env_version;
}
