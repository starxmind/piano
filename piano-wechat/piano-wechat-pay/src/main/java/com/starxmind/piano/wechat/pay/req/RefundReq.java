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
public class RefundReq {
    @NotNull(message = "Refund money is required")
    private BigDecimal refundMoney;
    @NotNull(message = "Total money is required")
    private BigDecimal totalMoney;
    @Builder.Default
    private String currency = "CNY";
    @NotBlank(message = "Param 'orderNo' must be not blank")
    private String orderNo;
    @NotBlank(message = "Param 'refundNo' must be not blank")
    private String refundNo;
    @NotBlank(message = "Param 'reason' must be not blank")
    private String reason;
    @NotBlank(message = "Param 'notifyUrl' must be not blank")
    private String notifyUrl;
}
