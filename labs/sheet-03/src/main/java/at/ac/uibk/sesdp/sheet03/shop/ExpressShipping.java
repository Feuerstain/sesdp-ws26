package at.ac.uibk.sesdp.sheet03.shop;

public class ExpressShipping implements ShippingMethod {

    private static final double BASE_RATE = 9.90;
    private static final double SHARE_OF_AMOUNT = 0.01;

    @Override
    public double costFor(Order order) {
        return BASE_RATE + order.getAmount() * SHARE_OF_AMOUNT;
    }
}
