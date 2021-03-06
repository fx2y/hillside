package cyou.ithan.consumer;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Log4j2
@Component
class CustomerClient {
    private final WebClient webClient;

    private String base = "localhost:8080";

    public void setBase(String base) {
        this.base = base;
        log.info("setting base to " + base);
    }

    CustomerClient(WebClient webClient) {
        this.webClient = webClient;
    }

    Flux<Customer> getAllCustomers() {
        return this.webClient
                .get()
                .uri("http://" + this.base + "/customers")
                .retrieve()
                .bodyToFlux(Customer.class);
    }
}