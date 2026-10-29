package at.ac.uibk.sesdp.sheet02.ioc;

final class TestTypes {

    private TestTypes() {
    }

    public interface Greeter {
        String greet();
    }

    public static class EnglishGreeter implements Greeter {
        @Override
        public String greet() {
            return "Hello";
        }
    }

    public static class GermanGreeter implements Greeter {
        @Override
        public String greet() {
            return "Hallo";
        }
    }

    public interface MessageService {
        String createMessage(String name);
    }

    public static class GreetingMessageService implements MessageService {
        private final Greeter greeter;

        public GreetingMessageService(Greeter greeter) {
            this.greeter = greeter;
        }

        @Override
        public String createMessage(String name) {
            return greeter.greet() + ", " + name + "!";
        }
    }

    public static class Controller {
        private final MessageService messageService;

        public Controller(MessageService messageService) {
            this.messageService = messageService;
        }

        public MessageService getMessageService() {
            return messageService;
        }
    }
}
