package com.truongvietdung.helpers;

import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.Charset;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Locale.LanguageRange;
import java.util.Map;
import java.util.Set;
import org.springframework.http.CacheControl;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRange;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;

public interface HttpHeaderBuilder {

  HttpHeaderBuilder setAccept(List<MediaType> acceptableMediaTypes);

  HttpHeaderBuilder setAcceptLanguage(List<LanguageRange> languages);

  HttpHeaderBuilder setAcceptLanguageAsLocales(List<Locale> locales);

  HttpHeaderBuilder setAccessControlAllowCredentials(boolean allowCredentials);

  HttpHeaderBuilder setAccessControlAllowHeaders(List<String> allowedHeaders);

  HttpHeaderBuilder setAccessControlAllowMethods(List<HttpMethod> allowedMethods);

  HttpHeaderBuilder setAccessControlAllowOrigin(String allowedOrigin);

  HttpHeaderBuilder setAccessControlExposeHeaders(List<String> exposedHeaders);

  HttpHeaderBuilder setAccessControlMaxAge(Duration maxAge);

  HttpHeaderBuilder setAccessControlMaxAge(long maxAge);

  HttpHeaderBuilder setAccessControlRequestHeaders(List<String> requestHeaders);

  HttpHeaderBuilder setAccessControlRequestMethod(HttpMethod requestMethod);

  HttpHeaderBuilder setAcceptCharset(List<Charset> acceptableCharsets);

  HttpHeaderBuilder setAllow(Set<HttpMethod> allowedMethods);

  HttpHeaderBuilder setBasicAuth(String username, String password);

  HttpHeaderBuilder setBasicAuth(String username, String password, Charset charset);

  HttpHeaderBuilder setBasicAuth(String encodedCredentials);

  HttpHeaderBuilder setBearerAuth(String token);

  HttpHeaderBuilder setCacheControl(CacheControl cacheControl);

  HttpHeaderBuilder setCacheControl(String cacheControl);

  HttpHeaderBuilder setConnection(String connection);

  HttpHeaderBuilder setConnection(List<String> connection);

  HttpHeaderBuilder setContentDispositionFormData(String name, String filename);

  HttpHeaderBuilder setContentDisposition(ContentDisposition contentDisposition);

  HttpHeaderBuilder setContentLanguage(Locale locale);

  HttpHeaderBuilder setContentLength(long contentLength);

  HttpHeaderBuilder setContentType(MediaType mediaType);

  HttpHeaderBuilder setDate(ZonedDateTime date);

  HttpHeaderBuilder setDate(Instant date);

  HttpHeaderBuilder setDate(long date);

  HttpHeaderBuilder setETag(String etag);

  HttpHeaderBuilder setExpires(ZonedDateTime expires);

  HttpHeaderBuilder setExpires(Instant expires);

  HttpHeaderBuilder setExpires(long expires);

  HttpHeaderBuilder setHost(InetSocketAddress host);

  HttpHeaderBuilder setIfMatch(String ifMatch);

  HttpHeaderBuilder setIfMatch(List<String> ifMatchList);

  HttpHeaderBuilder setIfModifiedSince(ZonedDateTime ifModifiedSince);

  HttpHeaderBuilder setIfModifiedSince(Instant ifModifiedSince);

  HttpHeaderBuilder setIfModifiedSince(long ifModifiedSince);

  HttpHeaderBuilder setIfNoneMatch(String ifNoneMatch);

  HttpHeaderBuilder setIfNoneMatch(List<String> ifNoneMatchList);

  HttpHeaderBuilder setIfUnmodifiedSince(ZonedDateTime ifUnmodifiedSince);

  HttpHeaderBuilder setIfUnmodifiedSince(Instant ifUnmodifiedSince);

  HttpHeaderBuilder setIfUnmodifiedSince(long ifUnmodifiedSince);

  HttpHeaderBuilder setLastModified(ZonedDateTime lastModified);

  HttpHeaderBuilder setLastModified(Instant lastModified);

  HttpHeaderBuilder setLastModified(long lastModified);

  HttpHeaderBuilder setLocation(URI location);

  HttpHeaderBuilder setOrigin(String origin);

  HttpHeaderBuilder setPragma(String pragma);

  HttpHeaderBuilder setRange(List<HttpRange> ranges);

  HttpHeaderBuilder setUpgrade(String upgrade);

  HttpHeaderBuilder setVary(List<String> requestHeaders);

  HttpHeaderBuilder setZonedDateTime(String headerName, ZonedDateTime date);

  HttpHeaderBuilder setDate(String headerName, long date);

  HttpHeaderBuilder add(String headerName, String headerValue);

  HttpHeaderBuilder addAll(String key, List<? extends String> values);

  HttpHeaderBuilder addAll(MultiValueMap<String, String> values);

  HttpHeaderBuilder set(String headerName, String headerValue);

  HttpHeaderBuilder setAll(Map<String, String> values);

  HttpHeaderBuilder putAll(Map<? extends String, ? extends List<String>> map);

}
