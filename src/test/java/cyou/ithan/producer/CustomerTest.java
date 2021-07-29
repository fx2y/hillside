package cyou.ithan.producer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.hamcrest.Matchers;
import org.hamcrest.MatcherAssert.*;

public class CustomerTest {

    @Test
    public void create() {
        Customer customer = new Customer("123", "foo");
        Assertions.assertEquals(customer.getId(), "123");
        org.assertj.core.api.Assertions.assertThat(customer.getName()).isEqualToIgnoringWhitespace("foo");
    }
}
