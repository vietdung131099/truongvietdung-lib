package com.truongvietdung.config;


import com.truongvietdung.config.interceptor.LoggingRequestInterceptor;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.net.ssl.SSLContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

@Configuration
@ConfigurationProperties("third-party")
public class ThirdPartyConfiguration extends LinkedHashMap<String, ApiInfo> {

  private Integer readTimeout = 15_000;
  private Integer connectTimeout = (int) (readTimeout * 0.05);
  private Integer maxPerRoute = 40;
  private Integer maxTotalConnection = 100;

  @Bean
  public RestTemplate thirdPartyRestTemplate()
      throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
    RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());

    if(ObjectUtils.isEmpty(restTemplate.getInterceptors())){
      restTemplate.setInterceptors(new ArrayList<>());
    }
    restTemplate.getInterceptors()
        .add(new LoggingRequestInterceptor());

    return restTemplate;
  }

  private ClientHttpRequestFactory getClientHttpRequestFactory()
      throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
    HttpComponentsClientHttpRequestFactory factory =
        new HttpComponentsClientHttpRequestFactory(getHttpClient(maxPerRoute, maxTotalConnection));
    factory.setConnectTimeout(connectTimeout);
    factory.setReadTimeout(readTimeout);
    return new BufferingClientHttpRequestFactory(factory);
  }

  private CloseableHttpClient getHttpClient(int maxPerRoute, int maxTotalConnection)
      throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
    Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
        .register("HTTPS", getSSLConnectionSocketFactory())
        .register("HTTP", PlainConnectionSocketFactory
            .getSocketFactory()).build();
    PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
    connectionManager.setDefaultMaxPerRoute(maxPerRoute);
    connectionManager.setMaxTotal(maxTotalConnection);
    SSLConnectionSocketFactory connSocketFactory = getSSLConnectionSocketFactory();
    return HttpClients.custom().setConnectionManager(connectionManager).setSSLSocketFactory(connSocketFactory).build();
  }

  private SSLConnectionSocketFactory getSSLConnectionSocketFactory()
      throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
    TrustStrategy trustStrategy = new TrustAllStrategy();
    SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, trustStrategy).build();
    String[] supportedProtocols = getSupportedProtocols();
    return new SSLConnectionSocketFactory(sslContext, supportedProtocols, null, new NoopHostnameVerifier());
  }

  private String[] getSupportedProtocols() {
    return new String[]{"TLSv1", "TLSv1.1", "TLSv1.2"};
  }


  public Integer getReadTimeout() {
    return readTimeout;
  }

  public void setReadTimeout(Integer readTimeout) {
    this.readTimeout = readTimeout;
  }

  public Integer getConnectTimeout() {
    return connectTimeout;
  }

  public void setConnectTimeout(Integer connectTimeout) {
    this.connectTimeout = connectTimeout;
  }

  public Integer getMaxPerRoute() {
    return maxPerRoute;
  }

  public void setMaxPerRoute(Integer maxPerRoute) {
    this.maxPerRoute = maxPerRoute;
  }

  public Integer getMaxTotalConnection() {
    return maxTotalConnection;
  }

  public void setMaxTotalConnection(Integer maxTotalConnection) {
    this.maxTotalConnection = maxTotalConnection;
  }
}
