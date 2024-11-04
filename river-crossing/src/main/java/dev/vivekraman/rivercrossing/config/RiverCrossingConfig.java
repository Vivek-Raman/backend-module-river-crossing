package dev.vivekraman.rivercrossing.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RiverCrossingConfig {
  @Bean
  public GroupedOpenApi riverCrossingApiGroup() {
    return GroupedOpenApi.builder()
        .group(Constants.MODULE_NAME)
        .packagesToScan("dev.vivekraman.rivercrossing.controller")
        .build();
  }
}
