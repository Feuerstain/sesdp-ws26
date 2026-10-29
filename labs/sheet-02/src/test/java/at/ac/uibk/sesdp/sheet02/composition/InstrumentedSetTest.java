package at.ac.uibk.sesdp.sheet02.composition;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.junit.jupiter.api.Test;

class InstrumentedSetTest {

    @Test
    void add_singleElements_countsEachCall() {
        InstrumentedSet<String> set = new InstrumentedSet<>(new HashSet<>());

        set.add("a");
        set.add("b");

        assertEquals(2, set.getAddCount());
    }

    @Test
    void addAll_threeElements_countsThree() {
        InstrumentedSet<String> set = new InstrumentedSet<>(new HashSet<>());

        set.addAll(List.of("a", "b", "c"));

        assertEquals(3, set.getAddCount());
    }

    @Test
    void addAndAddAll_mixed_countsEveryElementOnce() {
        InstrumentedSet<String> set = new InstrumentedSet<>(new HashSet<>());

        set.add("a");
        set.addAll(List.of("b", "c", "d"));

        assertEquals(4, set.getAddCount());
    }

    @Test
    void addAll_withTreeSet_worksWithAnySetImplementation() {
        InstrumentedSet<String> set = new InstrumentedSet<>(new TreeSet<>());

        set.addAll(List.of("c", "a", "b"));

        assertEquals(3, set.getAddCount());
        assertEquals(List.of("a", "b", "c"), List.copyOf(set));
    }

    @Test
    void forwardedMethods_behaveLikeTheInnerSet() {
        InstrumentedSet<String> set = new InstrumentedSet<>(new HashSet<>());
        set.addAll(List.of("a", "b"));

        assertEquals(2, set.size());
        assertTrue(set.contains("a"));
        assertEquals(Set.of("a", "b"), set);
    }
}
