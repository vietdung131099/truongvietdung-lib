package com.truongvietdung.config;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ApiInfo {
  private String baseUrl;
  private String authorization;
  private String authorizationHeader = "Authorization";
  private String gatewayPrefix;
}
