package at.ac.uibk.sesdp.sheet03.shop;

// One implementation per shipping method. A new method is a new class, no existing code changes.
public interface ShippingMethod {

    double costFor(Order order);
}
