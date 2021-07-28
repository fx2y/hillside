package cyou.ithan.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Log4j2
@RequiredArgsConstructor
public class DefaultClient {
    private final WebClient client;

    public Mono<Greeting> getSingle(String name) {
        return client.get()
                .uri("/greet/single/{name}", Map.of("name", name))
                .retrieve()
                .bodyToMono(Greeting.class);
    }

    public Flux<Greeting> getMany(String name) {
        return client.get()
                .uri("/greet/many/{name}", Map.of("name", name))
                .retrieve()
                .bodyToFlux(Greeting.class)
                .take(10);
    }

    public Mono<Greeting> getAuthenticatedGreeting() {
        return client.get()
                .uri("/greet/authenticated")
                .retrieve()
                .bodyToMono(Greeting.class);
    }
}