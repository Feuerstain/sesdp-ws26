package at.ac.uibk.sesdp.sheet03.shop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    void builder_setsEveryFieldByName() {
        Order order = Order.builder()
                .email("anna@example.com")
                .customer("Anna Huber")
                .id("o1")
                .amount(120.00)
                .status(OrderStatus.PAID)
                .build();

        assertEquals("o1", order.getId());
        assertEquals("Anna Huber", order.getCustomer());
        assertEquals("anna@example.com", order.getEmail());
        assertEquals(120.00, order.getAmount(), 1e-9);
        assertEquals(OrderStatus.PAID, order.getStatus());
    }

    @Test
    void builder_withoutStatus_createsNewOrder() {
        Order order = Order.builder().id("o1").customer("Anna Huber").email("anna@example.com").build();

        assertEquals(OrderStatus.NEW, order.getStatus());
    }

    @Test
    void builder_missingEmail_throws() {
        Order.Builder builder = Order.builder().id("o1").customer("Anna Huber");

        assertThrows(NullPointerException.class, builder::build);
    }
}
