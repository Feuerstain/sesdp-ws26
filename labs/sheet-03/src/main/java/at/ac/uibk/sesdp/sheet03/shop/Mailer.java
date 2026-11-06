package at.ac.uibk.sesdp.sheet03.shop;

// Abstraction for sending e-mails. OrderService only knows this interface.
public interface Mailer {

    void send(String to, String text);
}
