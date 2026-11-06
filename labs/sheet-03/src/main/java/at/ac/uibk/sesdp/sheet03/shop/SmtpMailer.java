package at.ac.uibk.sesdp.sheet03.shop;

// Low-level detail. Simulated: prints to the console instead of talking to a real SMTP server.
public class SmtpMailer implements Mailer {

    private final String host;
    private final int port;
    private boolean connected = false;

    public SmtpMailer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    // Connects by itself if needed, so callers cannot forget connect() or call it too late.
    @Override
    public void send(String to, String text) {
        if (!connected) {
            connect();
        }
        System.out.println("Mail to " + to + ": " + text);
    }

    public boolean isConnected() {
        return connected;
    }

    private void connect() {
        System.out.println("Connecting to " + host + ":" + port);
        connected = true;
    }
}
