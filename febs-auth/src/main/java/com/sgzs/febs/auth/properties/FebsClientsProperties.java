package com.sgzs.febs.auth.properties;

import lombok.Data;

/**
 * @author: jianyufeng
 * @description:
 * @date: 2020/5/29 17:31
 */
@Data
public class FebsClientsProperties {
    private String client;

    private String secret;

    private String grantType = "password,authorization_code,refresh_token";

    private String scope = "all";
}
