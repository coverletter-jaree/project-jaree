package org.jaree.config;

import java.util.Objects;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
  private final String API_VERSION = "v1";

  /**
   * Global prefix for API versioning
   */
  @Override
  public void configurePathMatch(@NonNull PathMatchConfigurer configurer) {
    String apiPrefixWithVersion = String.format("/api/%s", API_VERSION);
    configurer.addPathPrefix(Objects.requireNonNull(apiPrefixWithVersion), c -> true);
  }
}