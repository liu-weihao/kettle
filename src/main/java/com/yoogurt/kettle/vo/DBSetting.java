package com.yoogurt.kettle.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DBSetting {

    private String name;

    private String host;

    private String port = "3306";

    private String user = "root";

    private String password;

    public DBSetting(String host, String user, String password) {
        this.host = host;
        this.user = user;
        this.password = password;
    }
}
