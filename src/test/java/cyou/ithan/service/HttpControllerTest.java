package cyou.ithan.service;

import cyou.ithan.client.ClientProperties;
import cyou.ithan.client.DefaultClient;
import cyou.ithan.client.DefaultConfiguration;
import cyou.ithan.client.Greeting;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest(classes = {DefaultConfiguration.class, ClientProperties.class, HttpServiceApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {"spring.profiles.active=client", "server.port=8080", "spring.main.web-application-type=reactive"})
@ExtendWith(SpringExtension.class)
public class HttpControllerTest {
    @Autowired
    private DefaultClient defaultClient;

    @Autowired
    private DefaultClient authenticatedClient;

    @Test
    public void greetSingle() {
        Mono<Greeting> helloMono = this.defaultClient.getSingle("Single");
        StepVerifier.create(helloMono)
                .expectNextMatches(g -> g.getMessage().contains("Hello Single"))
                .verifyComplete();
    }

    @Test
    public void greetMany() {
        Flux<Greeting> helloFlux = this.defaultClient.getMany("Many").take(2);
        String msg = "Hello Many";
        StepVerifier.create(helloFlux)
                .expectNextMatches(g -> g.getMessage().contains(msg))
                .expectNextMatches(g -> g.getMessage().contains(msg)).verifyComplete();
    }

    @Test
    public void greetAuthenticatedGreeting() {
        Mono<Greeting> helloMono = this.authenticatedClient.getAuthenticatedGreeting();
        StepVerifier.create(helloMono)
                .expectNextMatches(g -> g.getMessage().contains("Hello username"))
                .verifyComplete();
    }
}