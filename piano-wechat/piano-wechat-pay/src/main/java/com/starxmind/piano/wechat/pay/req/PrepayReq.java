package com.starxmind.piano.wechat.pay.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class PrepayReq {
    @NotBlank(message = "Param 'appId' must be not blank")
    private String appId;
    @NotBlank(message = "Param 'orderNo' must be not blank")
    private String orderNo;
    @NotNull(message = "Total is required")
    private BigDecimal total;
    @NotBlank(message = "Param 'description' must be not blank")
    private String description;
    @NotBlank(message = "Param 'payerOpenid' must be not blank")
    private String payerOpenid;
    @NotBlank(message = "Param 'notifyUrl' must be not blank")
    private String notifyUrl;
}
