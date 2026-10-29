package at.ac.uibk.sesdp.sheet02.composition;

import java.util.HashSet;
import java.util.List;

// Runs both versions with the same sample input.
public class Demo {

    public static void main(String[] args) {
        List<String> input = List.of("a", "b", "c");

        InstrumentedHashSet<String> broken = new InstrumentedHashSet<>();
        broken.addAll(input);
        System.out.println("Inheritance (InstrumentedHashSet): addCount = " + broken.getAddCount());

        InstrumentedSet<String> fixed = new InstrumentedSet<>(new HashSet<>());
        fixed.addAll(input);
        System.out.println("Composition (InstrumentedSet):     addCount = " + fixed.getAddCount());
    }
}
