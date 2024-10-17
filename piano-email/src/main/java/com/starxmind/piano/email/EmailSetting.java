package com.starxmind.piano.email;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class EmailSetting {
    @NotBlank
    private String host;
    @NotNull
    @Min(0)
    private Integer port;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String nickname;
}
