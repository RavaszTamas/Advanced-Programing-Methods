package ro.ubb.catalog.web.config;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ro.ubb.catalog.core.config.JPAConfig;

@Configuration
@Profile("Test")
@ComponentScan({"ro.ubb.catalog.core"})
@ComponentScan({"ro.ubb.catalog.core.service"})
@Import({JPAConfig.class})
@PropertySources({
        @PropertySource(value = "classpath:local/db-test.properties"),
})
public class AppLocalConfigTest {

    /**
     * Enables placeholders usage with SpEL expressions.
     *
     * @return
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        System.out.println("#######");
        System.out.println("#######");
        System.out.println("##TEST##");
        System.out.println("#######");
        System.out.println("#######");
        return new PropertySourcesPlaceholderConfigurer();
    }
}
