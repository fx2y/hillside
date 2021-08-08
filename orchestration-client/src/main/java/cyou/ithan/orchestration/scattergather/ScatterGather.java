package cyou.ithan.orchestration.scattergather;

import cyou.ithan.orchestration.Customer;
import cyou.ithan.orchestration.Order;
import cyou.ithan.orchestration.Profile;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Component
class ScatterGather implements ApplicationListener<ApplicationReadyEvent> {
    private final CrmClient client;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        var ids = new Integer[]{1, 2, 7, 5};
        Flux<Customer> customerFlux = client.getCustomers(ids);
        Flux<Order> ordersFlux = client.getOrders(ids);
        Flux<CustomerOrders> customerOrdersFlux = customerFlux
                .flatMap(customer -> {
                    Mono<List<Order>> filteredOrdersFlux = ordersFlux
                            .filter(o -> o.getCustomerId().equals(customer.getId()))
                            .collectList();
                    Mono<Profile> profileMono = client.getProfile(customer.getId());
                    Mono<Customer> customerMono = Mono.just(customer);
                    return Flux.zip(customerMono, filteredOrdersFlux, profileMono);
                })
                .map(tuple -> new CustomerOrders(tuple.getT1(), tuple.getT2(), tuple.getT3()));
        for (var i = 0; i < 5; i++) run(customerOrdersFlux);
    }

    private void run(Flux<CustomerOrders> customerOrdersFlux) {
    }
}
