package cyou.ithan.orchestration.scattergather;

import cyou.ithan.orchestration.Customer;
import cyou.ithan.orchestration.Order;
import cyou.ithan.orchestration.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
class CrmClient {
    private final WebClient http;

    Flux<Customer> getCustomers(Integer[] ids) {
        var customersRoot = "http://customer-svc/customers?ids="
                + StringUtils.arrayToDelimitedString(ids, ",");
        return http.get().uri(customersRoot).retrieve().bodyToFlux(Customer.class);
    }

    Flux<Order> getOrders(Integer[] ids) {
        var ordersRoot = "http://order-svc/orders?ids="
                + StringUtils.arrayToDelimitedString(ids, ",");
        return http.get().uri(ordersRoot).retrieve().bodyToFlux(Order.class);
    }

    Mono<Profile> getProfile(Integer customerId) {
        var profilesRoot = "http://profile-svc/profiles/{id}";
        return http.get().uri(profilesRoot, customerId).retrieve()
                .bodyToMono(Profile.class);
    }
}
