package com.truongvietdung;

import com.truongvietdung.config.ApiInfo;
import com.truongvietdung.config.ThirdPartyConfiguration;
import com.truongvietdung.helpers.IRestTemplateBuilder;
import com.truongvietdung.helpers.RestTemplateBuilder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

@Data
public abstract class Base3rdParty {

  @Autowired
  ThirdPartyConfiguration thirdPartyConfiguration;

  @Autowired
  RestTemplate thirdPartyRestTemplate;

  public String getUrl(String uri) {
    String baseUrl = thirdPartyConfiguration.get(getKeyConfig()).getBaseUrl() + "/" + uri;
    return baseUrl.replaceAll("/+", "/")
        .replaceAll("(https?:)/+", "$1//");
  }

  public abstract String getKeyConfig();

  public IRestTemplateBuilder setAuth(IRestTemplateBuilder restTemplateBuilder) {
    ApiInfo apiInfo = getApiInfoConfig();
    restTemplateBuilder.headersBuilder()
        .add(apiInfo.getAuthorizationHeader(), apiInfo.getAuthorization());
    return restTemplateBuilder;
  }

  public ApiInfo getApiInfoConfig() {
    return thirdPartyConfiguration.get(getKeyConfig());
  }

  public IRestTemplateBuilder get(String uri) {
    IRestTemplateBuilder restTemplateBuilder = RestTemplateBuilder
        .getInstance(thirdPartyRestTemplate, HttpMethod.GET)
        .setUrl(getUrl(uri));

    return setAuth(restTemplateBuilder);
  }

  public  IRestTemplateBuilder post(String uri) {
    IRestTemplateBuilder restTemplateBuilder = RestTemplateBuilder
        .getInstance(thirdPartyRestTemplate, HttpMethod.POST)
        .setUrl(getUrl(uri));

    return setAuth(restTemplateBuilder);
  }

  public <B> IRestTemplateBuilder put(String uri, B body) {
    IRestTemplateBuilder restTemplateBuilder = RestTemplateBuilder
        .getInstance(thirdPartyRestTemplate, HttpMethod.PUT, body)
        .setUrl(getUrl(uri));

    return setAuth(restTemplateBuilder);
  }

  public <B> IRestTemplateBuilder post(String uri, B body) {
    IRestTemplateBuilder restTemplateBuilder = RestTemplateBuilder.getInstance(thirdPartyRestTemplate, HttpMethod.POST, body)
        .setUrl(getUrl(uri));

    return setAuth(restTemplateBuilder);
  }

  public IRestTemplateBuilder delete(String uri){
    IRestTemplateBuilder restTemplateBuilder = RestTemplateBuilder.getInstance(thirdPartyRestTemplate, HttpMethod.DELETE)
        .setUrl(getUrl(uri));

    return setAuth(restTemplateBuilder);
  }

}
