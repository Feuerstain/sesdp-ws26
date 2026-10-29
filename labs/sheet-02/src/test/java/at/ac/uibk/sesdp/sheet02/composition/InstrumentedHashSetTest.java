package at.ac.uibk.sesdp.sheet02.composition;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

// Documents the bug in the original inheritance-based version.
class InstrumentedHashSetTest {

    @Test
    void add_singleElements_countsCorrectly() {
        InstrumentedHashSet<String> set = new InstrumentedHashSet<>();

        set.add("a");
        set.add("b");

        assertEquals(2, set.getAddCount());
    }

    @Test
    void addAll_threeElements_countsSixInsteadOfThree() {
        InstrumentedHashSet<String> set = new InstrumentedHashSet<>();

        set.addAll(List.of("a", "b", "c"));

        // Bug: HashSet.addAll calls the overridden add() for each element -> counted twice
        assertEquals(6, set.getAddCount());
    }
}
