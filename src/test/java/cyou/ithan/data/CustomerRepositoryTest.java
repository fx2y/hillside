package cyou.ithan.data;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CustomerRepositoryTest extends BaseCustomerRepositoryTest {
    @Autowired
    private SimpleCustomerRepository customerRepository;

    @Override
    public SimpleCustomerRepository getRepository() {
        return this.customerRepository;
    }
}