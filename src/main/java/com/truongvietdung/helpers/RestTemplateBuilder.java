package com.truongvietdung.helpers;

import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Locale.LanguageRange;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.CacheControl;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRange;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
public class RestTemplateBuilder<BodyType> implements IRestTemplateBuilder, UrlBuilder, HttpHeaderBuilder {

  private final HttpHeaders httpHeaders = new HttpHeaders();
  private HttpMethod httpMethod;
  private BodyType body;
  private RestTemplate restTemplate;
  private UriComponentsBuilder builder;

  public static IRestTemplateBuilder getInstance(RestTemplate restTemplate, HttpMethod httpMethod) {
    RestTemplateBuilder<Object> restTemplateBuilder = new RestTemplateBuilder<>();
    restTemplateBuilder.restTemplate = restTemplate;
    restTemplateBuilder.body = null;
    restTemplateBuilder.setContentType(MediaType.APPLICATION_JSON);
    restTemplateBuilder.setHttpMethod(httpMethod);
    return restTemplateBuilder;
  }

  public static <B> IRestTemplateBuilder getInstance(RestTemplate restTemplate, HttpMethod httpMethod, B body) {
    RestTemplateBuilder<B> restTemplateBuilder = new RestTemplateBuilder<>();
    restTemplateBuilder.restTemplate = restTemplate;
    restTemplateBuilder.body = body;
    restTemplateBuilder.setContentType(MediaType.APPLICATION_JSON);
    restTemplateBuilder.setHttpMethod(httpMethod);
    return restTemplateBuilder;
  }

  @Override
  public IRestTemplateBuilder setHttpMethod(HttpMethod httpMethod) {
    this.httpMethod = httpMethod;
    return this;
  }

  @Override
  public IRestTemplateBuilder setUrl(String url) {
    this.builder = UriComponentsBuilder.fromUriString(url);
    return this;
  }

  @Override
  public UrlBuilder urlBuilder(String url) {
    this.builder = UriComponentsBuilder.fromUriString(url);
    return this;
  }

  @Override
  public UrlBuilder urlBuilder() {
    return this;
  }

  @Override
  public HttpHeaderBuilder headersBuilder() {
    return this;
  }

  @Override
  public <ResponseType> ResponseType getResponse(ParameterizedTypeReference<ResponseType> responseType) {
    HttpEntity<BodyType> entity;
    if(ObjectUtils.isEmpty(this.body)) {
      entity = new HttpEntity<>(this.httpHeaders);
    } else {
      entity = new HttpEntity<>(this.body, this.httpHeaders);
    }

    try {
      return restTemplate.exchange(this.builder.build().toUri(), this.httpMethod, entity, responseType).getBody();
    } catch(HttpClientErrorException | HttpServerErrorException e) {
      int httpStatusCode = e.getRawStatusCode();
      String message = e.getResponseBodyAsString();
      log.error("Fail when communicating to 3rd [{}: {}, {}, {}]", this.httpMethod, this.builder.build().toUriString(), httpStatusCode, message);
      throw e;
    } catch(ResourceAccessException e) {
      String message = e.getMessage();
      log.error("Fail when communicating to 3rd [{}: {}, {}]", this.httpMethod, this.builder.build().toUriString(), message);
      throw e;
    }
  }

  @Override
  public <ResponseType> ResponseType getResponse(Class<ResponseType> responseType) {
    HttpEntity<BodyType> entity;
    if (ObjectUtils.isEmpty(this.body)) {
      entity = new HttpEntity<>(this.httpHeaders);
    } else {
      entity = new HttpEntity<>(body, httpHeaders);
    }

    try {
      return restTemplate.exchange(this.builder.build().toUri(), this.httpMethod, entity, responseType).getBody();
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      int httpStatusCode = e.getRawStatusCode();
      String message = e.getResponseBodyAsString();
      log.error("Fail when communicating to 3rd [{}: {}, {}, {}]", this.httpMethod, this.builder.build().toUriString(), httpStatusCode, message);
      throw e;
    } catch (ResourceAccessException e) {
      String message = e.getMessage();
      log.error("Fail when communicating to 3rd [{}: {}, {}]", this.httpMethod, this.builder.build().toUriString(), message);
      throw e;
    }
  }

  @Override
  public <T> ResponseEntity<T> getResponseEntity(Class<T> responseType) {
    HttpEntity<BodyType> entity;
    if (ObjectUtils.isEmpty(this.body)) {
      entity = new HttpEntity<>(this.httpHeaders);
    } else {
      entity = new HttpEntity<>(body, httpHeaders);
    }

    try {
      return restTemplate.exchange(this.builder.build().toUri(), this.httpMethod, entity, responseType);
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      int httpStatusCode = e.getRawStatusCode();
      String message = e.getResponseBodyAsString();
      log.error("Fail when communicating to 3rd [{}: {}, {}, {}]", this.httpMethod, this.builder.build().toUriString(), httpStatusCode, message);
      throw e;
    } catch (ResourceAccessException e) {
      String message = e.getMessage();
      log.error("Fail when communicating to 3rd [{}: {}, {}]", this.httpMethod, this.builder.build().toUriString(), message);
      throw e;
    }
  }

  @Override
  public <T> ResponseEntity<T> getResponseEntity(ParameterizedTypeReference<T> responseType) {
    HttpEntity<BodyType> entity;
    if (ObjectUtils.isEmpty(this.body)) {
      entity = new HttpEntity<>(this.httpHeaders);
    } else {
      entity = new HttpEntity<>(body, httpHeaders);
    }

    try {
      return restTemplate.exchange(this.builder.build().toUri(), this.httpMethod, entity, responseType);
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      int httpStatusCode = e.getRawStatusCode();
      String message = e.getResponseBodyAsString();
      log.error("Fail when communicating to 3rd [{}: {}, {}, {}]", this.httpMethod, this.builder.build().toUriString(), httpStatusCode, message);
      throw e;
    } catch (ResourceAccessException e) {
      String message = e.getMessage();
      log.error("Fail when communicating to 3rd [{}: {}, {}]", this.httpMethod, this.builder.build().toUriString(), message);
      throw e;
    }
  }

  @Override
  public UrlBuilder queryParam(String name, Object... values) {
    builder.queryParam(name, values);
    return this;
  }

  @Override
  public UrlBuilder queryParam(String name, Collection<?> values) {
    builder.queryParam(name, values);
    return this;
  }

  @Override
  public UrlBuilder queryParam(MultiValueMap<String, String> params) {
    builder.queryParams(params);
    return this;
  }

  @Override
  public HttpHeaderBuilder setAccept(List<MediaType> acceptableMediaTypes) {
    this.httpHeaders.setAccept(acceptableMediaTypes);
    return this;
  }

  @Override
  public HttpHeaderBuilder setAcceptLanguage(List<LanguageRange> languages) {
    this.httpHeaders.setAcceptLanguage(languages);
    return this;
  }

  @Override
  public HttpHeaderBuilder setAcceptLanguageAsLocales(List<Locale> locales) {
    this.httpHeaders.setAcceptLanguageAsLocales(locales);
    return this;
  }

  @Override
  public HttpHeaderBuilder setAccessControlAllowCredentials(boolean allowCredentials) {
    this.httpHeaders.setAccessControlAllowCredentials(allowCredentials);
    return this;
  }

  @Override
  public HttpHeaderBuilder setAccessControlAllowHeaders(List<String> allowedHeaders) {
    this.httpHeaders.setAccessControlAllowHeaders(allowedHeaders);
    return null;
  }

  @Override
  public HttpHeaderBuilder setAccessControlAllowMethods(List<HttpMethod> allowedMethods) {
    this.httpHeaders.setAccessControlAllowMethods(allowedMethods);
    return this;
  }

  @Override
  public HttpHeaderBuilder setAccessControlAllowOrigin(String allowedOrigin) {
    this.httpHeaders.setAccessControlAllowOrigin(allowedOrigin);
    return this;
  }

  @Override
  public HttpHeaderBuilder setAccessControlExposeHeaders(List<String> exposedHeaders) {
    this.httpHeaders.setAccessControlExposeHeaders(exposedHeaders);
    return this;
  }

  @Override
  public HttpHeaderBuilder setAccessControlMaxAge(Duration maxAge) {
    this.httpHeaders.setAccessControlMaxAge(maxAge);
    return this;
  }

  @Override
  public HttpHeaderBuilder setAccessControlMaxAge(long maxAge) {
    this.httpHeaders.setAccessControlMaxAge(maxAge);
    return this;
  }

  @Override
  public HttpHeaderBuilder setAccessControlRequestHeaders(List<String> requestHeaders) {
    this.httpHeaders.setAccessControlRequestHeaders(requestHeaders);
    return this;
  }

  @Override
  public HttpHeaderBuilder setAccessControlRequestMethod(HttpMethod requestMethod) {
    this.httpHeaders.setAccessControlRequestMethod(requestMethod);
    return this;
  }

  @Override
  public HttpHeaderBuilder setAcceptCharset(List<Charset> acceptableCharsets) {
    this.httpHeaders.setAcceptCharset(acceptableCharsets);
    return this;
  }

  @Override
  public HttpHeaderBuilder setAllow(Set<HttpMethod> allowedMethods) {
    this.httpHeaders.setAllow(allowedMethods);
    return this;
  }

  @Override
  public HttpHeaderBuilder setBasicAuth(String username, String password) {
    this.httpHeaders.setBasicAuth(username, password);
    return this;
  }

  @Override
  public HttpHeaderBuilder setBasicAuth(String username, String password, Charset charset) {
    this.httpHeaders.setBasicAuth(username, password, charset);
    return this;
  }

  @Override
  public HttpHeaderBuilder setBasicAuth(String encodedCredentials) {
    this.httpHeaders.setBasicAuth(encodedCredentials);
    return this;
  }

  @Override
  public HttpHeaderBuilder setBearerAuth(String token) {
    this.httpHeaders.setBearerAuth(token);
    return this;
  }

  @Override
  public HttpHeaderBuilder setCacheControl(CacheControl cacheControl) {
    this.httpHeaders.setCacheControl(cacheControl);
    return this;
  }

  @Override
  public HttpHeaderBuilder setCacheControl(String cacheControl) {
    this.httpHeaders.setCacheControl(cacheControl);
    return this;
  }

  @Override
  public HttpHeaderBuilder setConnection(String connection) {
    this.httpHeaders.setConnection(connection);
    return this;
  }

  @Override
  public HttpHeaderBuilder setConnection(List<String> connection) {
    this.httpHeaders.setConnection(connection);
    return this;
  }

  @Override
  public HttpHeaderBuilder setContentDispositionFormData(String name, String filename) {
    this.httpHeaders.setContentDispositionFormData(name, filename);
    return this;
  }

  @Override
  public HttpHeaderBuilder setContentDisposition(ContentDisposition contentDisposition) {
    this.httpHeaders.setContentDisposition(contentDisposition);
    return this;
  }

  @Override
  public HttpHeaderBuilder setContentLanguage(Locale locale) {
    this.httpHeaders.setContentLanguage(locale);
    return this;
  }

  @Override
  public HttpHeaderBuilder setContentLength(long contentLength) {
    this.httpHeaders.setContentLength(contentLength);
    return this;
  }

  @Override
  public HttpHeaderBuilder setContentType(MediaType mediaType) {
    this.httpHeaders.setContentType(mediaType);
    return this;
  }

  @Override
  public HttpHeaderBuilder setDate(ZonedDateTime date) {
    this.httpHeaders.setDate(date);
    return this;
  }

  @Override
  public HttpHeaderBuilder setDate(Instant date) {
    this.httpHeaders.setDate(date);
    return this;
  }

  @Override
  public HttpHeaderBuilder setDate(long date) {
    this.httpHeaders.setDate(date);
    return this;
  }

  @Override
  public HttpHeaderBuilder setETag(String etag) {
    this.httpHeaders.setETag(etag);
    return this;
  }

  @Override
  public HttpHeaderBuilder setExpires(ZonedDateTime expires) {
    this.httpHeaders.setExpires(expires);
    return this;
  }

  @Override
  public HttpHeaderBuilder setExpires(Instant expires) {
    this.httpHeaders.setExpires(expires);
    return this;
  }

  @Override
  public HttpHeaderBuilder setExpires(long expires) {
    this.httpHeaders.setExpires(expires);
    return this;
  }

  @Override
  public HttpHeaderBuilder setHost(InetSocketAddress host) {
    this.httpHeaders.setHost(host);
    return this;
  }

  @Override
  public HttpHeaderBuilder setIfMatch(String ifMatch) {
    this.httpHeaders.setIfMatch(ifMatch);
    return this;
  }

  @Override
  public HttpHeaderBuilder setIfMatch(List<String> ifMatchList) {
    this.httpHeaders.setIfMatch(ifMatchList);
    return this;
  }

  @Override
  public HttpHeaderBuilder setIfModifiedSince(ZonedDateTime ifModifiedSince) {
    this.httpHeaders.setIfModifiedSince(ifModifiedSince);
    return this;
  }

  @Override
  public HttpHeaderBuilder setIfModifiedSince(Instant ifModifiedSince) {
    this.httpHeaders.setIfModifiedSince(ifModifiedSince);
    return this;
  }

  @Override
  public HttpHeaderBuilder setIfModifiedSince(long ifModifiedSince) {
    this.httpHeaders.setIfModifiedSince(ifModifiedSince);
    return this;
  }

  @Override
  public HttpHeaderBuilder setIfNoneMatch(String ifNoneMatch) {
    this.httpHeaders.setIfNoneMatch(ifNoneMatch);
    return this;
  }

  @Override
  public HttpHeaderBuilder setIfNoneMatch(List<String> ifNoneMatchList) {
    this.httpHeaders.setIfNoneMatch(ifNoneMatchList);
    return this;
  }

  @Override
  public HttpHeaderBuilder setIfUnmodifiedSince(ZonedDateTime ifUnmodifiedSince) {
    this.httpHeaders.setIfUnmodifiedSince(ifUnmodifiedSince);
    return this;
  }

  @Override
  public HttpHeaderBuilder setIfUnmodifiedSince(Instant ifUnmodifiedSince) {
    this.httpHeaders.setIfUnmodifiedSince(ifUnmodifiedSince);
    return this;
  }

  @Override
  public HttpHeaderBuilder setIfUnmodifiedSince(long ifUnmodifiedSince) {
    this.httpHeaders.setIfUnmodifiedSince(ifUnmodifiedSince);
    return this;
  }

  @Override
  public HttpHeaderBuilder setLastModified(ZonedDateTime lastModified) {
    this.httpHeaders.setLastModified(lastModified);
    return this;
  }

  @Override
  public HttpHeaderBuilder setLastModified(Instant lastModified) {
    this.httpHeaders.setLastModified(lastModified);
    return this;
  }

  @Override
  public HttpHeaderBuilder setLastModified(long lastModified) {
    this.httpHeaders.setLastModified(lastModified);
    return this;
  }

  @Override
  public HttpHeaderBuilder setLocation(URI location) {
    this.httpHeaders.setLocation(location);
    return this;
  }

  @Override
  public HttpHeaderBuilder setOrigin(String origin) {
    this.httpHeaders.setOrigin(origin);
    return this;
  }

  @Override
  public HttpHeaderBuilder setPragma(String pragma) {
    this.httpHeaders.setPragma(pragma);
    return this;
  }

  @Override
  public HttpHeaderBuilder setRange(List<HttpRange> ranges) {
    this.httpHeaders.setRange(ranges);
    return this;
  }

  @Override
  public HttpHeaderBuilder setUpgrade(String upgrade) {
    this.httpHeaders.setUpgrade(upgrade);
    return this;
  }

  @Override
  public HttpHeaderBuilder setVary(List<String> requestHeaders) {
    this.httpHeaders.setVary(requestHeaders);
    return this;
  }

  @Override
  public HttpHeaderBuilder setZonedDateTime(String headerName, ZonedDateTime date) {
    this.httpHeaders.setZonedDateTime(headerName, date);
    return this;
  }

  @Override
  public HttpHeaderBuilder setDate(String headerName, long date) {
    this.httpHeaders.setDate(headerName, date);
    return this;
  }

  @Override
  public HttpHeaderBuilder add(String headerName, String headerValue) {
    this.httpHeaders.add(headerName, headerValue);
    return this;
  }

  @Override
  public HttpHeaderBuilder addAll(String key, List<? extends String> values) {
    this.httpHeaders.addAll(key, values);
    return this;
  }

  @Override
  public HttpHeaderBuilder addAll(MultiValueMap<String, String> values) {
    this.httpHeaders.addAll(values);
    return this;
  }

  @Override
  public HttpHeaderBuilder set(String headerName, String headerValue) {
    this.httpHeaders.set(headerName, headerValue);
    return this;
  }

  @Override
  public HttpHeaderBuilder setAll(Map<String, String> values) {
    this.httpHeaders.setAll(values);
    return this;
  }

  @Override
  public HttpHeaderBuilder putAll(Map<? extends String, ? extends List<String>> map) {
    this.httpHeaders.putAll(map);
    return this;
  }
}
