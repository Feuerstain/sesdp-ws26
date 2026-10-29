package at.ac.uibk.sesdp.sheet02.composition;

import java.util.Collection;
import java.util.HashSet;

// Original code from the exercise sheet (Lombok replaced by hand-written code).
// BROKEN: HashSet.addAll calls add() for every element, so addAll counts each element twice.
public class InstrumentedHashSet<E> extends HashSet<E> {

    private int addCount = 0;

    public InstrumentedHashSet() {
    }

    public InstrumentedHashSet(int initCap, float loadFactor) {
        super(initCap, loadFactor);
    }

    @Override
    public boolean add(E e) {
        addCount++;
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return super.addAll(c);
    }

    public int getAddCount() {
        return addCount;
    }
}
