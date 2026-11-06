package at.ac.uibk.sesdp.sheet03.shop;

public class StandardShipping implements ShippingMethod {

    private static final double FLAT_RATE = 4.90;

    @Override
    public double costFor(Order order) {
        return FLAT_RATE;
    }
}
