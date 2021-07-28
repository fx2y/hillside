package cyou.ithan.client;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;

@Log4j2
@Configuration
public class DefaultConfiguration {
    @Bean
    DefaultClient defaultClient(WebClient.Builder builder, ClientProperties properties) {
        var root = properties.getHttp().getRootUrl();
        return new DefaultClient(builder.baseUrl(root).build());
    }

    @Bean
    DefaultClient authenticatedClient(WebClient.Builder builder, ClientProperties clientProperties) {
        var httpProperties = clientProperties.getHttp();
        var basicAuthProperties = clientProperties.getHttp().getBasic();
        var filterFunction = ExchangeFilterFunctions.basicAuthentication(
                basicAuthProperties.getUsername(), basicAuthProperties.getPassword());
        WebClient client = builder
                .baseUrl(httpProperties.getRootUrl())
                .filters(filters -> filters.add(filterFunction))
                .build();
        return new DefaultClient(client);
    }
}