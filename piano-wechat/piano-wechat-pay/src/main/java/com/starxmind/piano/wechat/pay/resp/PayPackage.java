package com.starxmind.piano.wechat.pay.resp;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@AllArgsConstructor
@Data
public class PayPackage {
    private String prepayId;
    private Map<String, Object> payPackage;
}
