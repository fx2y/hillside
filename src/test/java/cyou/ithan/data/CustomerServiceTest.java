package cyou.ithan.data;

import groovy.util.logging.Log4j2;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Log4j2
@SpringBootTest
@EnableTransactionManagement
@ExtendWith(SpringExtension.class)
public class CustomerServiceTest extends BaseCustomerServiceTest {
    @Autowired
    private SimpleCustomerRepository customerRepository;

    @Override
    public SimpleCustomerRepository getCustomerRepository() {
        return this.customerRepository;
    }
}