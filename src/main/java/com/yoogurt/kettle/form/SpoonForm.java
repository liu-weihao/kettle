package com.yoogurt.kettle.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Setter
@Getter
@NoArgsConstructor
public class SpoonForm {

    @NotBlank(message = "请指定源数据源")
    private String from = "PROD";

    @NotBlank(message = "请指定目标数据源")
    private String to = "TEST";

    @NotBlank(message = "请指定数据库")
    private String db;

    @NotBlank(message = "请输入授权码")
    private String token;

    private String ipv4;
}
