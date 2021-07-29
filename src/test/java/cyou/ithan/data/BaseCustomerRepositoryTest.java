package cyou.ithan.data;

import groovy.util.logging.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.InputStreamReader;

@Log4j2
public abstract class BaseCustomerRepositoryTest {
    @Autowired
    private CustomerDatabaseInitializer initializer;
    @Value("classpath:/schema.sql")
    private Resource resource;
    private String sql;

    public abstract SimpleCustomerRepository getRepository();

    @BeforeEach
    public void setupResource() throws Exception {
        Assertions.assertTrue(this.resource.exists());
        try (var in = new InputStreamReader(this.resource.getInputStream())) {
            this.sql = FileCopyUtils.copyToString(in);
        }
    }

    @Test
    public void delete() {
        var repository = this.getRepository();
        var data = repository.findAll().flatMap(c -> repository.deleteById(c.getId()))
                .thenMany(Flux.just(
                        new Customer(null, "first@email.com"),
                        new Customer(null, "second@email.com"),
                        new Customer(null, "third@email.com")))
                .flatMap(repository::save);

        StepVerifier
                .create(data)
                .expectNextCount(3)
                .verifyComplete();

        StepVerifier
                .create(repository.findAll().take(1)
                        .flatMap(customer -> repository.deleteById(customer.getId())))
                .verifyComplete();

        StepVerifier
                .create(repository.findAll())
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    public void saveAndFindAll() {
        var repository = this.getRepository();

        StepVerifier.create(this.initializer.resetCustomerTable()).verifyComplete();

        var insert = Flux.just(
                        new Customer(null, "first@email.com"),
                        new Customer(null, "second@email.com"),
                        new Customer(null, "third@email.com"))
                .flatMap(repository::save);

        StepVerifier
                .create(insert)
                .expectNextCount(2)
                .expectNextMatches(customer -> customer.getId() != null
                        && customer.getId() > 0 && customer.getEmail() != null)
                .verifyComplete();
    }

    @Test
    public void findById() {
        var repository = this.getRepository();

        var insert = Flux.just(
                        new Customer(null, "first@email.com"),
                        new Customer(null, "second@email.com"),
                        new Customer(null, "third@email.com"))
                .flatMap(repository::save);
        var all = repository.findAll().flatMap(c -> repository.deleteById(c.getId()))
                .thenMany(insert.thenMany(repository.findAll()));

        StepVerifier.create(all).expectNextCount(3).verifyComplete();

        var recordsById = repository.findAll()
                .flatMap(customer -> Mono.zip(Mono.just(customer),
                        repository.findById(customer.getId())))
                .filterWhen(tuple2 -> Mono.just(tuple2.getT1().equals(tuple2.getT2())));

        StepVerifier.create(recordsById).expectNextCount(3).verifyComplete();
    }

    @Test
    public void update() {
        var repository = this.getRepository();

        StepVerifier
                .create(this.initializer.resetCustomerTable())
                .verifyComplete();

        var email = "test@again.com";
        var save = repository.save(new Customer(null, email));
        StepVerifier
                .create(save)
                .expectNextMatches(p -> p.getId() != null)
                .verifyComplete();
        StepVerifier
                .create(repository.findAll())
                .expectNextCount(1)
                .verifyComplete();
        var updateFlux = repository
                .findAll()
                .map(c -> new Customer(c.getId(), c.getEmail().toUpperCase()))
                .flatMap(repository::update);
        StepVerifier
                .create(updateFlux)
                .expectNextMatches(c -> c.getEmail().equals(email.toUpperCase()))
                .verifyComplete();
    }
}
