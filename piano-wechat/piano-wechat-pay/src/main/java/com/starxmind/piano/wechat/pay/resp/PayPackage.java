package com.starxmind.piano.wechat.pay.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PayPackage {
    private String prepayId;
    private Map<String, Object> payPackage;
}
