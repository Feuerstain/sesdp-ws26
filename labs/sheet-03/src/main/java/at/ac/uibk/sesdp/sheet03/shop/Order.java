package at.ac.uibk.sesdp.sheet03.shop;

import java.util.Objects;

// Plain data class. Built with named builder methods instead of five positional arguments,
// so customer and email can no longer be swapped by accident.
public class Order {

    private final String id;
    private final String customer;
    private final String email;
    private final double amount;
    private final OrderStatus status;

    private Order(Builder builder) {
        this.id = Objects.requireNonNull(builder.id, "id");
        this.customer = Objects.requireNonNull(builder.customer, "customer");
        this.email = Objects.requireNonNull(builder.email, "email");
        this.amount = builder.amount;
        this.status = builder.status;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public String getCustomer() {
        return customer;
    }

    public String getEmail() {
        return email;
    }

    public double getAmount() {
        return amount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public static class Builder {

        private String id;
        private String customer;
        private String email;
        private double amount;
        private OrderStatus status = OrderStatus.NEW;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder customer(String customer) {
            this.customer = customer;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder amount(double amount) {
            this.amount = amount;
            return this;
        }

        public Builder status(OrderStatus status) {
            this.status = status;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
