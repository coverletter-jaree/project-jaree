package org.jaree.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private static final String API_VERSION = "v1";

    /**
     * Global prefix for API versioning
     */
    @Override
    @SuppressWarnings("null")
    public void configurePathMatch(PathMatchConfigurer configurer) {
        String apiPrefixWithVersion = String.format("/api/%s", API_VERSION);
        configurer.addPathPrefix(apiPrefixWithVersion, c -> true);
    }
}
