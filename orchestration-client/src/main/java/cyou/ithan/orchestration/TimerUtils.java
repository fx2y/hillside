package cyou.ithan.orchestration;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicLong;

@Log4j2
public abstract class TimerUtils {
    public static <T> Mono<T> cache(Mono<T> cache) {
        return cache.doOnNext(c -> log.debug("receiving " + c.toString())).cache();
    }

    public static <T> Flux<T> cache(Flux<T> cache) {
        return cache.doOnNext(c -> log.debug("receiving " + c.toString())).cache();
    }

    public static <T> Mono<T> monitor(Mono<T> configMono) {
        var start = new AtomicLong();
        return configMono
                .doOnError(exception -> log.error("oops!", exception))
                .doOnSubscribe(subscription -> start.set(System.currentTimeMillis()))
                .doOnNext(greeting -> log.info("total time: {}", System.currentTimeMillis() - start.get()));
    }

    public static <T> Flux<T> monitor(Flux<T> configFlux) {
        var start = new AtomicLong();
        return configFlux
                .doOnError(exception -> log.error("oops!", exception))
                .doOnSubscribe((subscription) -> start.set(System.currentTimeMillis()))
                .doOnNext((greeting) -> log.info("total time: {}", System.currentTimeMillis() - start.get()));
    }
}