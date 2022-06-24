package com.truongvietdung.helpers;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public interface IRestTemplateBuilder {
  IRestTemplateBuilder setUrl(String url);

  UrlBuilder urlBuilder(String url);

  UrlBuilder urlBuilder();

  IRestTemplateBuilder setHttpMethod(HttpMethod httpMethod);

  HttpHeaderBuilder headersBuilder();

  <T> T getResponse(ParameterizedTypeReference<T> responseType);

  <T> T getResponse(Class<T> responseType);

  <T> ResponseEntity<T> getResponseEntity(Class<T> responseType);

  <T> ResponseEntity<T> getResponseEntity(ParameterizedTypeReference<T> responseType);
}
