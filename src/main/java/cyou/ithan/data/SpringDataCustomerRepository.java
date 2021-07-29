package cyou.ithan.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
class SpringDataCustomerRepository implements SimpleCustomerRepository {
    private final CustomerRepository repository;

    @Override
    public Mono<Customer> save(Customer c) {
        return repository.save(c);
    }

    @Override
    public Mono<Customer> update(Customer c) {
        return repository.save(c);
    }

    @Override
    public Mono<Customer> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Mono<Void> deleteById(Integer id) {
        return repository.deleteById(id);
    }

    @Override
    public Flux<Customer> findAll() {
        return repository.findAll();
    }
}
