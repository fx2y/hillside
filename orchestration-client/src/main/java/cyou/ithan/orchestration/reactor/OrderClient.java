package cyou.ithan.orchestration.reactor;

import cyou.ithan.orchestration.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
class OrderClient {
    private final WebClient http;

    Flux<Order> getOrders(Integer... ids) {
        var ordersRoot = "http://order-svc/orders?ids=" + StringUtils.arrayToDelimitedString(ids, ",");
        return http.get().uri(ordersRoot).retrieve().bodyToFlux(Order.class);
    }
}
