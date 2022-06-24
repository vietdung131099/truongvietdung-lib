package com.truongvietdung.helpers;


import java.util.Collection;
import org.springframework.lang.Nullable;
import org.springframework.util.MultiValueMap;

public interface UrlBuilder extends IRestTemplateBuilder{

  UrlBuilder queryParam(String name,Object... values);

  UrlBuilder queryParam(String name,@Nullable Collection<?> values);

  UrlBuilder queryParam(MultiValueMap<String, String> params);

}
