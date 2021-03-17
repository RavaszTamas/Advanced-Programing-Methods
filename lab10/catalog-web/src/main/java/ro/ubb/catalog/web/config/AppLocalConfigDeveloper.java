package ro.ubb.catalog.web.config;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ro.ubb.catalog.core.config.JPAConfig;

@Configuration
@Profile("Developer")
@ComponentScan({"ro.ubb.catalog.core"})
@ComponentScan({"ro.ubb.catalog.core.service"})
@Import({JPAConfig.class})
@PropertySources({
  @PropertySource(value = "classpath:local/db-dev.properties"),
})
public class AppLocalConfigDeveloper implements AppLocalConfigInterface {

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    System.out.println("#######");
    System.out.println("#######");
    System.out.println("##DEV##");
    System.out.println("#######");
    System.out.println("#######");
    return new PropertySourcesPlaceholderConfigurer();
  }
}
