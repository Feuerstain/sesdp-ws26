package at.ac.uibk.sesdp.sheet02.ioc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import at.ac.uibk.sesdp.sheet02.ioc.TestTypes.Controller;
import at.ac.uibk.sesdp.sheet02.ioc.TestTypes.EnglishGreeter;
import at.ac.uibk.sesdp.sheet02.ioc.TestTypes.GermanGreeter;
import at.ac.uibk.sesdp.sheet02.ioc.TestTypes.Greeter;
import at.ac.uibk.sesdp.sheet02.ioc.TestTypes.GreetingMessageService;
import at.ac.uibk.sesdp.sheet02.ioc.TestTypes.MessageService;
import org.junit.jupiter.api.Test;

class MinimalContainerTest {

    private final MinimalContainer container = new MinimalContainer();

    @Test
    void create_registeredInterface_returnsRegisteredImplementation() {
        container.register(Greeter.class, EnglishGreeter.class);

        Greeter greeter = container.create(Greeter.class);

        assertInstanceOf(EnglishGreeter.class, greeter);
    }

    @Test
    void create_classWithDependency_injectsRegisteredImplementation() {
        container.register(Greeter.class, EnglishGreeter.class);
        container.register(MessageService.class, GreetingMessageService.class);

        MessageService service = container.create(MessageService.class);

        assertEquals("Hello, Anna!", service.createMessage("Anna"));
    }

    @Test
    void create_nestedDependencies_resolvesWholeGraph() {
        container.register(Greeter.class, EnglishGreeter.class);
        container.register(MessageService.class, GreetingMessageService.class);

        Controller controller = container.create(Controller.class);

        assertEquals("Hello, Anna!", controller.getMessageService().createMessage("Anna"));
    }

    @Test
    void create_afterChangingRegistration_usesNewImplementation() {
        container.register(Greeter.class, EnglishGreeter.class);
        container.register(Greeter.class, GermanGreeter.class);
        container.register(MessageService.class, GreetingMessageService.class);

        MessageService service = container.create(MessageService.class);

        assertEquals("Hallo, Anna!", service.createMessage("Anna"));
    }

    @Test
    void create_calledTwice_returnsNewInstances() {
        container.register(Greeter.class, EnglishGreeter.class);

        Greeter first = container.create(Greeter.class);
        Greeter second = container.create(Greeter.class);

        assertNotSame(first, second);
    }

    @Test
    void create_unregisteredInterface_throws() {
        assertThrows(IllegalStateException.class, () -> container.create(Greeter.class));
    }

    @Test
    void create_missingNestedRegistration_throws() {
        container.register(MessageService.class, GreetingMessageService.class);

        assertThrows(IllegalStateException.class, () -> container.create(Controller.class));
    }
}
