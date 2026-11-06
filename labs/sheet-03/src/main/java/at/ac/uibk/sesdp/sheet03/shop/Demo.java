package at.ac.uibk.sesdp.sheet03.shop;

// The only place that knows the concrete classes: it builds them and hands them to OrderService.
public class Demo {

    public static void main(String[] args) {
        FileOrderStore store = new FileOrderStore("orders.db");
        OrderService service = new OrderService(store, new SmtpMailer("smtp.shop.at", 587));

        Order order = Order.builder()
                .id("o1")
                .customer("Anna Huber")
                .email("anna@example.com")
                .amount(120.00)
                .build();
        service.placeOrder(order);

        System.out.println("Stored status: " + store.load("o1").getStatus());
        System.out.println("Standard shipping: " + service.shippingCost(order, new StandardShipping()));
        System.out.println("Express shipping:  " + service.shippingCost(order, new ExpressShipping()));
    }
}
