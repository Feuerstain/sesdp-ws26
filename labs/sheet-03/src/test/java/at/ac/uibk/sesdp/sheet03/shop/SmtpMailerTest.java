package at.ac.uibk.sesdp.sheet03.shop;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SmtpMailerTest {

    private final SmtpMailer mailer = new SmtpMailer("smtp.shop.at", 587);

    @Test
    void newMailer_isNotConnected() {
        assertFalse(mailer.isConnected());
    }

    @Test
    void send_withoutExplicitConnect_connectsByItself() {
        mailer.send("anna@example.com", "Your order o1 was received");

        assertTrue(mailer.isConnected());
    }
}
