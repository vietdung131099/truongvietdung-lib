package com.truongvietdung.config.interceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

@Slf4j
public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

  @Override
  public ClientHttpResponse intercept(
      HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
    ClientHttpResponse response = execution.execute(request, body);
    if(log.isDebugEnabled()){
      log(request, body, response);
    }
    return response;
  }

  private void log(HttpRequest request, byte[] requestBody, ClientHttpResponse response) throws IOException {
    StringBuilder inputStringBuilder = new StringBuilder();
    Charset charset;

    try{
      charset = response.getHeaders().getContentType().getCharset();
    } catch (InvalidMediaTypeException ignored){
      charset = Charset.defaultCharset();
    }

    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), charset));
    String line = bufferedReader.readLine();
    while (line != null) {
      inputStringBuilder.append(line);
      inputStringBuilder.append('\n');
      line = bufferedReader.readLine();
    }

    Map<String, Map<String, Object>> mapLog = new HashMap<>();
    Map<String, Object> mapRequest = new HashMap<>();
    Map<String, Object> mapResponse = new HashMap<>();

    mapLog.put("request", mapRequest);
    mapLog.put("response", mapResponse);

    mapRequest.put("uri", request.getURI());
    mapRequest.put("method", request.getMethod());
    mapRequest.put("headers", request.getHeaders());
    mapRequest.put("body", new String(requestBody, "UTF-8"));

    mapResponse.put("status_code", response.getStatusCode());
    mapResponse.put("status_text", response.getStatusText());
    mapResponse.put("headers", response.getHeaders());
    mapResponse.put("body", inputStringBuilder);

//    log.debug(JsonUtil.objectToJson(mapLog));

  }

}
