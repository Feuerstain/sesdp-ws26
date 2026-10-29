package at.ac.uibk.sesdp.sheet02.ioc;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class MinimalContainer {

    // interface -> implementation, e.g. Greeter -> EnglishGreeter
    private final Map<Class<?>, Class<?>> types = new HashMap<>();

    public <I, C extends I> void register(Class<I> iface, Class<C> implementation) {
        types.put(iface, implementation);
    }

    public <I> I create(Class<I> iface) {
        return iface.cast(createInstance(iface));
    }

    private Object createInstance(Class<?> type) {
        // 1. Find the class to build: the registered implementation, or the type itself
        Class<?> implementation = types.getOrDefault(type, type);
        if (implementation.isInterface()) {
            throw new IllegalStateException("No implementation registered for " + type.getSimpleName());
        }

        // 2. Take its public constructor
        Constructor<?> constructor = implementation.getConstructors()[0];

        // 3. Create every constructor parameter first (recursively)
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        Object[] arguments = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            arguments[i] = createInstance(parameterTypes[i]);
        }

        // 4. Call the constructor with the created dependencies
        try {
            return constructor.newInstance(arguments);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Could not create " + implementation.getSimpleName(), e);
        }
    }
}
