package com.starxmind.piano.wechat.pay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class PayReq {
    @NotNull(message = "Total is required")
    @Min(value = 1, message = "Param 'total' must be greater than 0")
    private Double total;
    @NotBlank(message = "Param 'description' must be not blank")
    private String description;
    @NotBlank(message = "Param 'notifyUrl' must be not blank")
    private String notifyUrl;
    @NotBlank(message = "Param 'orderNo' must be not blank")
    private String orderNo;
    private String payerOpenid;
}
