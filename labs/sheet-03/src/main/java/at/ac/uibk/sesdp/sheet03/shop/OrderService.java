package at.ac.uibk.sesdp.sheet03.shop;

// High-level logic. Depends only on abstractions, which are passed in from outside.
public class OrderService {

    private final OrderWriter store;
    private final Mailer mailer;

    public OrderService(OrderWriter store, Mailer mailer) {
        this.store = store;
        this.mailer = mailer;
    }

    public void placeOrder(Order order) {
        mailer.send(order.getEmail(), "Your order " + order.getId() + " was received");
        store.save(order);
    }

    public double shippingCost(Order order, ShippingMethod method) {
        return method.costFor(order);
    }
}
