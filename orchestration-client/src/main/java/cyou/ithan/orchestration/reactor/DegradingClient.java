package cyou.ithan.orchestration.reactor;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
class DegradingClient implements ApplicationListener<ApplicationReadyEvent> {
    private final OrderClient client;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        this.client.getOrders(1, 2)
                .onErrorResume(e -> Flux.empty())
                .subscribe(System.out::println);
    }
}
