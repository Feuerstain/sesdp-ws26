package at.ac.uibk.sesdp.sheet03.shop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

// Runs without a file and without an SMTP server: both are replaced by lambdas (DIP).
class OrderServiceTest {

    private final List<Order> savedOrders = new ArrayList<>();
    private final List<String> sentMails = new ArrayList<>();
    private final OrderService service =
            new OrderService(savedOrders::add, (to, text) -> sentMails.add(to + " <- " + text));

    private final Order order = Order.builder()
            .id("o1")
            .customer("Anna Huber")
            .email("anna@example.com")
            .amount(200.00)
            .build();

    @Test
    void placeOrder_savesTheOrder() {
        service.placeOrder(order);

        assertEquals(List.of(order), savedOrders);
    }

    @Test
    void placeOrder_sendsConfirmationToTheOrdersEmail() {
        service.placeOrder(order);

        assertEquals(List.of("anna@example.com <- Your order o1 was received"), sentMails);
    }

    @Test
    void shippingCost_usesTheGivenMethod() {
        assertEquals(4.90, service.shippingCost(order, new StandardShipping()), 1e-9);
        assertEquals(11.90, service.shippingCost(order, new ExpressShipping()), 1e-9);
    }

    @Test
    void shippingCost_newMethod_needsNoChangeInOrderService() {
        ShippingMethod pickup = pickedUpOrder -> 0.0;

        assertEquals(0.0, service.shippingCost(order, pickup), 1e-9);
    }
}
