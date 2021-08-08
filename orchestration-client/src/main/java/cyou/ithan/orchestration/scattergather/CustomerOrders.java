package cyou.ithan.orchestration.scattergather;

import cyou.ithan.orchestration.Customer;
import cyou.ithan.orchestration.Order;
import cyou.ithan.orchestration.Profile;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@Data
@RequiredArgsConstructor
class CustomerOrders {
    private final Customer customer;
    private final Collection<Order> orders;
    private final Profile profile;
}
